function pageLoad() {

    let ReservationHTML = `<table>` +
        '<tr>' +
        '<th>GuestID</th>' +
        '<th>RoomID</th>' +
        '<th>StaffID</th>' +
        '<th class="last">Options</th>' +
        '</tr>';

    fetch('/Reservation/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Reservation => {

        for (let reservation of Reservation) {

            ReservationHTML += `<tr>` +
                `<th>${reservation.GuestID}</th>` +
                `<th>${reservation.RoomID}</th>` +
                `<th>${reservation.StaffID}</th>` +
                `<th><button class="editButton" data-id=${reservation.GuestID}>Edit</button></th>` +
                `<th><button class='deleteButton' data-id='${reservation.GuestID}'>Delete</button></th>` +
                `</tr>`;
        }

        ReservationHTML += '</table>';

        document.getElementById("listDiv").innerHTML = ReservationHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editReservation);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteReservation);
        }

    });

    document.getElementById("saveButton").addEventListener("click", saveEditReservation);
    document.getElementById("cancelButton").addEventListener("click", clearEditReservation);

    document.getElementById("editHeading").innerHTML = 'Add new reservation:';

}

function editReservation(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new reservation:';

        document.getElementById("GuestID").value = '';
        document.getElementById("RoomID").value = '';
        document.getElementById("StaffID").value = '';

    } else {

        fetch('/Reservation/get/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(reservation => {

            if (reservation.hasOwnProperty('error')) {
                alert(reservation.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + reservation.GuestID + ':';

                document.getElementById("GuestID").value = reservation.GuestID;
                document.getElementById("RoomID").value = reservation.RoomID;
                document.getElementById("StaffID").value = reservation.StaffID;

            }

        });

    }

}

function saveEditReservation(event) {

    event.preventDefault();

    if (document.getElementById("GuestID").value.trim() === '') {
        alert("Please provide the guest ID.");
        return;
    }

    if (document.getElementById("RoomID").value.trim() === '') {
        alert("Please provide the room ID.");
        return;
    }

    if (document.getElementById("StaffID").value.trim() === '') {
        alert("Please provide the staff ID.");
        return;
    }
    const id = document.getElementById("GuestID").value;
    const form = document.getElementById("ReservationForm");
    const formData = new FormData(form);

    let apiPath;
    if (id === '') {
        apiPath = '/Reservation/new';
    } else {
        apiPath = '/Reservation/update';
    }

    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            pageLoad();
            clearEditReservation();
        }
    });
}

function clearEditReservation(event) {

    document.getElementById("GuestID").value = '';
    document.getElementById("RoomID").value = '';
    document.getElementById("StaffID").value = '';

    document.getElementById("editHeading").innerHTML = 'Add new reservation:';

    if (event !== undefined && event !== null) {
        event.preventDefault();
    }

}

function deleteReservation(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("GuestID", id);

        fetch('/ReservationReservation/delete', {method: 'post', body: formData}
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