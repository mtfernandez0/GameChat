package com.mati.gamechat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mati.gamechat.entity.lol.Region;
import com.mati.gamechat.dto.lolDto.ChampionMasteryIdDto;
import com.mati.gamechat.dto.lolDto.LeagueEntryDTO;
import com.mati.gamechat.dto.lolDto.SummonerDTO;
import com.mati.gamechat.entity.lol.Champion;
import com.mati.gamechat.service.lol.ChampionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service in charge of connecting to the RiotAPI.
 */
@Service
public class WebClientLolService {
    @Value("${apiTransmissionProtocol}")
    private String protocol;
    @Value("${apiLolUrl}")
    private String lolUrl;
    @Value("${apiUrlSummoner}")
    private String apiUrlSummoner;
    @Value("${apiUrlLeague}")
    private String apiUrlLeague;
    @Value("${lolApiKey}")
    private String lolApiKey;
    @Value("${apiUrlChampionMasteries}")
    private String apiUrlChampions;
    @Value("${dataDragonUrl}")
    private String dDragonUrl;

    private final WebClient client;
    private final ChampionService championService;

    public WebClientLolService(ChampionService championService){
        this.championService = championService;
        client = WebClient.create();
    }

    public SummonerDTO getMonoSummoner(String nickname,
                                       Region region) throws HttpServerErrorException{

        Mono<SummonerDTO> monoSummonerDto = client.get()
                .uri(protocol + region + lolUrl + apiUrlSummoner + nickname)
                .header("X-Riot-Token", lolApiKey)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError())
                        return clientResponse.createException().flatMap(Mono::error);
                    else if (clientResponse.statusCode().is5xxServerError())
                        throw new HttpServerErrorException(
                                HttpStatusCode.valueOf(
                                        clientResponse.statusCode().value()),
                                        "Service not available.");
                    else
                        return clientResponse.bodyToMono(SummonerDTO.class);
                });
        return monoSummonerDto.block();
    }

    public Set<LeagueEntryDTO> getLeagueEntryMonoSet(String summonerId,
                                                     Region region) throws HttpServerErrorException {

        Mono<Set<LeagueEntryDTO>> monoSet = client.get()
                .uri(protocol + region + lolUrl + apiUrlLeague + summonerId)
                .header("X-Riot-Token", lolApiKey)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError())
                        return clientResponse.createException().flatMap(Mono::error);
                    else if (clientResponse.statusCode().is5xxServerError())
                        throw new HttpServerErrorException(
                                HttpStatusCode.valueOf(
                                        clientResponse.statusCode().value()),
                                "Service not available.");
                    else
                        return clientResponse.bodyToMono(new ParameterizedTypeReference<>() {});
                });
        return monoSet.block();
    }

    public List<Long> getChampionIds(String summonerId, Region region) throws HttpServerErrorException {

        List<ChampionMasteryIdDto> championIdsDto = client.get()
                .uri(protocol + region.name() + lolUrl + apiUrlChampions + summonerId)
                .header("X-Riot-Token", lolApiKey)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is5xxServerError())
                        throw new HttpServerErrorException(
                                HttpStatusCode.valueOf(
                                        clientResponse.statusCode().value()),
                                "Service not available.");
                    else
                        return clientResponse.bodyToMono(new ParameterizedTypeReference<List<ChampionMasteryIdDto>>() {});
                }).block();

        List<Long> ids = new ArrayList<>();

        for (int i = 0; i < Math.min(3, championIdsDto.size()) ; i++) {
            ids.add(championIdsDto.get(i).getChampionId());
        }

        return ids;
    }

    public List<String> getTopChampionNames(String summonerId, Region region) throws JsonProcessingException {
        List<Long> ids = getChampionIds(summonerId, region);
        List<String> names = new ArrayList<>();

        if (!championService.existsAll(ids))
            championService.saveAll(getChampionsData());

        List<Champion> champions = championService.findAllById(ids);
        champions.forEach(a -> names.add(a.getName()));

        return names;
    }

    public List<Champion> getChampionsData() throws JsonProcessingException {
        String json = client.get()
                .uri(dDragonUrl)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is5xxServerError())
                        throw new HttpServerErrorException(
                                HttpStatusCode.valueOf(
                                        clientResponse.statusCode().value()),
                                "Service not available.");
                    else
                        return clientResponse.bodyToMono(String.class);
                }).block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        JsonNode dataNode = root.get("data");
        List<Champion> champions = new ArrayList<>();

        for (JsonNode championNode : dataNode) {
            String name = championNode.get("name").asText();
            Long id = championNode.get("key").asLong();

            champions.add(new Champion(id, name));
        }

        return champions;
    }
}
