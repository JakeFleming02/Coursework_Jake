function pageLoad() {

    let GuestHTML = `<table>` +
        '<tr>' +
        '<th>GuestID</th>' +
        '<th>GuestName</th>' +
        '<th>GuestArrive</th>' +
        '<th>GuestLeave</th>' +
        '<th>VIP</th>' +
        '<th class="last">Options</th>' +
        '</tr>';

    fetch('/Guest/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Guest => {

        for (let guest of Guest) {

            GuestHTML += `<tr>` +
                `<th>${guest.GuestID}</th>` +
                `<th>${guest.GuestName}</th>` +
                `<th>${guest.GuestArrive}</th>` +
                `<th>${guest.GuestLeave}</th>` +
                `<th>${guest.VIP}</th>` +
                `<th><button class="editButton" data-id=${guest.GuestID}>Edit</button></th>` +
                `<th><button class='deleteButton' data-id='${guest.GuestID}'>Delete</button></th>` +
                `</tr>`;
        }

        GuestHTML += '</table>';

        document.getElementById("listDiv").innerHTML = GuestHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editGuest);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteGuest);
        }

    });

    document.getElementById("saveButton").addEventListener("click", saveEditGuest);
    document.getElementById("cancelButton").addEventListener("click", clearEditGuest);

    document.getElementById("editHeading").innerHTML = 'Add new guest:';

}

function editGuest(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new guest:';

        document.getElementById("GuestID").value = '';
        document.getElementById("GuestName").value = '';
        document.getElementById("GuestArrive").value = '';
        document.getElementById("GuestLeave").value = '';
        document.getElementById("VIP").checked = false;

    } else {

        fetch('/Guest/get/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(guest => {

            if (guest.hasOwnProperty('error')) {
                alert(guest.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + guest.GuestName + ':';

                document.getElementById("GuestID").value = guest.GuestID;
                document.getElementById("GuestName").value = guest.GuestName;
                document.getElementById("GuestArrive").value = guest.GuestArrive;
                document.getElementById("GuestLeave").value = guest.GuestLeave;
                document.getElementById("VIP").value = guest.VIP;

            }

        });

    }

}

function saveEditGuest(event) {

    event.preventDefault();

    if (document.getElementById("GuestName").value.trim() === '') {
        alert("Please provide a guest name.");
        return;
    }

    if (document.getElementById("GuestArrive").value.trim() === '') {
        alert("Please provide the guests date of arrival.");
        return;
    }

    if (document.getElementById("GuestLeave").value.trim() === '') {
        alert("Please provide the guests date of departure.");
        return;
    }

    const id = document.getElementById("GuestID").value;
    const form = document.getElementById("GuestForm");
    const formData = new FormData(form);

    let apiPath;
    if (id === '') {
        apiPath = '/Guest/new';
    } else {
        apiPath = '/Guest/update';
    }

    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            pageLoad();
            clearEditGuest();
        }
    });
}

function clearEditGuest(event) {

    document.getElementById("GuestID").value = '';
    document.getElementById("GuestName").value = '';
    document.getElementById("GuestArrive").value = '';
    document.getElementById("GuestLeave").value = '';
    document.getElementById("VIP").checked = false;

    document.getElementById("editHeading").innerHTML = 'Add new guest:';

    if (event !== undefined && event !== null) {
        event.preventDefault();
    }

}

function deleteGuest(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("GuestID", id);

        fetch('/Guest/delete', {method: 'post', body: formData}
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