function pageLoad() {

    let ReservationHTML = `<table>` +
        '<tr>' +
        '<th>ReservationID</th>' +
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
                `<th>${reservation.ReservationID}</th>` +
                `<th>${reservation.GuestID}</th>` +
                `<th>${reservation.RoomID}</th>` +
                `<th>${reservation.StaffID}</th>` +
                `<th><button class="editButton" data-id=${reservation.ReservationID}>Edit</button></th>` +
                `<th><button class='deleteButton' data-id='${reservation.ReservationID}'>Delete</button></th>` +
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


    document.getElementById("IndexButton").addEventListener("click", indexRedirect);
    document.getElementById("StaffButton").addEventListener("click", staffRedirect);
    document.getElementById("GuestButton").addEventListener("click", guestRedirect);
    document.getElementById("FeaturesButton").addEventListener("click", featuresRedirect);

    document.getElementById("saveButton").addEventListener("click", saveEditReservation);
    document.getElementById("cancelButton").addEventListener("click", clearEditReservation);

    document.getElementById("editHeading").innerHTML = 'Add new reservation:';

}

function editReservation(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new reservation:';

        document.getElementById("ReservationID").value = '';
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

                document.getElementById("ReservationID").value = reservation.ReservationID;
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
    const id = document.getElementById("ReservationID").value;
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

    document.getElementById("ReservationID").value = '';
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
        formData.append("ReservationID", id);

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

function indexRedirect(){
    window.location.href="/client/index.html"
}
function staffRedirect(){
    window.location.href="/client/staff.html"
}
function guestRedirect(){
    window.location.href="/client/guest.html"
}
function featuresRedirect(){
    window.location.href="/client/features.html"
}