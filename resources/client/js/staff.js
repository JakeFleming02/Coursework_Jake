function pageLoad() {

    let StaffHTML = `<table>` +
        '<tr>' +
        '<th>StaffID</th>' +
        '<th>StaffName</th>' +
        '<th class="last">Options</th>' +
        '</tr>';

    fetch('/Staff/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Staff => {

        for (let staff of Staff) {

            StaffHTML += `<tr>` +
                `<th>${staff.StaffID}</th>` +
                `<th>${staff.StaffName}</th>` +
                `<th><button class="editButton" data-id=${staff.StaffID}>Edit</button></th>` +
                `<th><button class='deleteButton' data-id='${staff.StaffID}'>Delete</button></th>` +
                `</tr>`;
        }

        StaffHTML += '</table>';

        document.getElementById("listDiv").innerHTML = StaffHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editStaff);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteStaff);
        }

    });


    document.getElementById("IndexButton").addEventListener("click", indexRedirect);
    document.getElementById("GuestButton").addEventListener("click", guestRedirect);
    document.getElementById("ReservationButton").addEventListener("click", reservationRedirect);
    document.getElementById("FeaturesButton").addEventListener("click", featuresRedirect);

    document.getElementById("saveButton").addEventListener("click", saveEditStaff);
    document.getElementById("cancelButton").addEventListener("click", clearEditStaff);

    document.getElementById("editHeading").innerHTML = 'Add new staff:';

}

function editStaff(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new staff:';

        document.getElementById("StaffID").value = '';
        document.getElementById("StaffName").value = '';
    } else {

        fetch('/Staff/get/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(staff => {

            if (staff.hasOwnProperty('error')) {
                alert(staff.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + staff.StaffName + ':';

                document.getElementById("StaffID").value = staff.StaffID;
                document.getElementById("StaffName").value = staff.StaffName;

            }

        });

    }

}

function saveEditStaff(event) {

    event.preventDefault();

    if (document.getElementById("StaffName").value.trim() === '') {
        alert("Please provide a staff name.");
        return;
    }

    const id = document.getElementById("StaffID").value;
    const form = document.getElementById("StaffForm");
    const formData = new FormData(form);

    let apiPath;
    if (id === '') {
        apiPath = '/Staff/new';
    } else {
        apiPath = '/Staff/update';
    }

    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            pageLoad();
            clearEditStaff();
        }
    });
}

function clearEditStaff(event) {

    document.getElementById("StaffID").value = '';
    document.getElementById("StaffName").value = '';

    document.getElementById("editHeading").innerHTML = 'Add new staff:';

    if (event !== undefined && event !== null) {
        event.preventDefault();
    }

}

function deleteStaff(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("StaffID", id);

        fetch('/Staff/delete', {method: 'post', body: formData}
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
function guestRedirect(){
    window.location.href="/client/guest.html"
}
function reservationRedirect(){
    window.location.href="/client/reservation.html"
}
function featuresRedirect(){
    window.location.href="/client/features.html"
}