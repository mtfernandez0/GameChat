package com.mati.gamechat.entity.lol;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class QueueTypeConverter implements AttributeConverter<QueueType, String> {
    @Override
    public String convertToDatabaseColumn(QueueType attribute) {
        return attribute.getQueueType();
    }

    @Override
    public QueueType convertToEntityAttribute(String dbData) {
        for (QueueType q: QueueType.values())
            if (dbData.equals(q.getQueueType())) return q;
        throw new IllegalArgumentException("Couldn't find an according representation");
    }
}
