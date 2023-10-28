'use strict';

function join(groupId){
    let joinBtn = document.getElementById('group-btn-' + groupId);

    (async () => {
        try{
            await $.ajax({
                type: 'PUT',
                url: '/group/join/'+groupId
            });

            joinBtn.innerText = 'Leave';
            joinBtn.setAttribute('onclick', 'leave('+groupId+')');
            joinBtn.classList = 'btn btn-outline-danger';

        } catch (error){
            console.log("error")
            alert(error)
        }
    });
}

function leave(groupId){
    let leaveBtn = document.getElementById('group-btn-' + groupId);

    (async () => {
        try{
            await $.ajax({
                type: 'PUT',
                url: '/group/leave/'+groupId
            });

            leaveBtn.innerText = 'Join';
            leaveBtn.setAttribute('onclick', 'join('+groupId+')');
            leaveBtn.classList = 'btn btn-outline-success';

        } catch (error){
            alert(error)
        }
    });
}