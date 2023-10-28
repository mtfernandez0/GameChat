$(function (){
    $("#friendRequestForm").on("submit", (e) => {
        $.ajax({
            type: "POST",
            url: "/sendFriendRequest",
            data: $("#friendRequestForm").serialize(),
            success: (e) => {
                $("#friendForm").html("<div id='success'><div>")
                $("#success").html("<h2>Friend request sent successfully!</h2>")
            },
            error: (request, status, error) => {
                $("#friendForm").append("<h3 style='color: red'>" + error.message + "</h3>")
            }
        });
        e.preventDefault()
    })
})