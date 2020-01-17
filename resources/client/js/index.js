function pageLoad() {

    let RoomHTML = `<table>` +
        '<tr>' +
        '<th>RoomID</th>' +
        '<th>RoomName</th>' +
        '<th>RoomLocation</th>' +
        '<th>Cleaned</th>' +
        '<th>Checked</th>' +
        '<th>OutOfOrder</th>' +
        '<th class="last">Options</th>' +
        '</tr>';

    fetch('/Room/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Room => {

        for (let room of Room) {

            RoomHTML += `<tr>` +
                `<th>${room.RoomID}</th>` +
                `<th>${room.RoomName}</th>` +
                `<th>${room.RoomLocation}</th>` +
                `<th>${room.Cleaned}</th>` +
                `<th>${room.Checked}</th>` +
                `<th>${room.OutOfOrder}</th>` +
                `<th><button class="editButton" data-id=${room.RoomID}>Edit</button></th>` +
                `<th><button class='deleteButton' data-id='${room.RoomID}'>Delete</button></th>` +
                `</tr>`;
        }

        RoomHTML += '</table>';

        document.getElementById("listDiv").innerHTML = RoomHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editRoom);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteRoom);
        }

    });

    //document.getElementById("IndexButton").addEventListener("click", indexRedirect);  <-- nope

    document.getElementById("saveButton").addEventListener("click", saveEditRoom);
    document.getElementById("cancelButton").addEventListener("click", clearEditRoom);

    document.getElementById("editHeading").innerHTML = 'Add new room:';

}

function editRoom(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new room:';

        document.getElementById("RoomID").value = '';
        document.getElementById("RoomName").value = '';
        document.getElementById("RoomLocation").value = '';
        document.getElementById("Cleaned").checked = false;
        document.getElementById("Checked").checked = false;
        document.getElementById("OutOfOrder").checked = false;

    } else {

        fetch('/Room/get/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(room => {

            if (room.hasOwnProperty('error')) {
                alert(room.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + room.RoomName + ':';

                document.getElementById("RoomID").value = room.RoomID;
                document.getElementById("RoomName").value = room.RoomName;
                document.getElementById("RoomLocation").value = room.RoomLocation;
                document.getElementById("Cleaned").checked = room.Cleaned;
                document.getElementById("Checked").checked = room.Checked;
                document.getElementById("OutOfOrder").checked = room.OutOfOrder;

            }

        });

    }

}

function saveEditRoom(event) {

    event.preventDefault();

    if (document.getElementById("RoomName").value.trim() === '') {
        alert("Please provide a room name.");
        return;
    }

    if (document.getElementById("RoomLocation").value.trim() === '') {
        alert("Please provide the room location.");
        return;
    }


    const id = document.getElementById("RoomID").value;
    const form = document.getElementById("RoomForm");
    const formData = new FormData(form);

    let apiPath;

    console.log(id);

    if (id === '') {
        apiPath = '/Room/new';
    } else {
        apiPath = '/Room/update';
    }

    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            pageLoad();
            clearEditRoom();
        }
    });
}

function clearEditRoom(event) {

    document.getElementById("RoomID").value = '';
    document.getElementById("RoomName").value = '';
    document.getElementById("RoomLocation").value = '';
    document.getElementById("Cleaned").checked = false;
    document.getElementById("Checked").checked = false;
    document.getElementById("OutOfOrder").checked = false;

    document.getElementById("editHeading").innerHTML = 'Add new room:';

    if (event !== undefined && event !== null) {
        event.preventDefault();
    }

}

function deleteRoom(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("RoomID", id);

        fetch('/Room/delete', {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {

                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoad();
                }
            }
        );
    }
}