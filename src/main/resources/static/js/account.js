$(function (){
    $("#save-lol-profile").on("click", (e) => {
        console.log("entered")
        const replaceButton = document.getElementById("save-lol-profile")
        const waitingButton = '<button id="waiting-lol-profile" type="button" class="btn btn-secondary" disabled>Editing...</button>'
        replaceButton.insertAdjacentHTML('afterend', waitingButton)
        replaceButton.remove()
        let nickname = $("input[name='nickname']").val();
        let region = $("select[name='region']").val();

        $.ajax({
            type: "POST",
            url: "/saveLolProfile",
            data: { nickname: nickname, region: region},
            success: (e) => {
                location.reload()
            },
            error: (request, status, error) => {
                console.log(error)
            }
        });
    })
})