function pageLoad() {

    let GuestHTML = '<table>' +
        '<tr>' +
        '<th>GuestID</th>' +
        '<th>GuestName</th>' +
        '<th>GuestArrive</th>' +
        '<th>GuestLeave</th>' +
        '<th>VIP</th>' +
        '</tr>';

    fetch('/Guest/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Guest => {
        for (let guest of Guest) {

            GuestHTML += `<tr>` +
                `<td>${guest.GuestID}</td>` +
                `<td>${guest.GuestName}</td>` +
                `<td>${guest.GuestArrive}</td>` +
                `<td>${guest.GuestLeave}</td>` +
                `<td>${guest.VIP}</td>` +
                `<td class="last">` +
                `<button class='editButton' data-id='${guest.GuestID}'>Edit</button>` +
                `<button class='deleteButton' data-id='${guest.GuestID}'>Delete</button>` +
                `</td>` +
                `</tr>`;

        }
    })

    GuestHTML += '</table>';

    document.getElementById("listDiv").innerHTML = GuestHTML;

    let editButtons = document.getElementsByClassName("updateButton");
    for (let button of editButtons) {
        button.addEventListener("click", updateGuest);
    }

    let deleteButtons = document.getElementsByClassName("deleteButton");
    for (let button of deleteButtons) {
        button.addEventListener("click", deleteGuest);
    }
}

document.getElementById("saveButton").addEventListener("click", saveUpdateGuest);
document.getElementById("cancelButton").addEventListener("click", cancelUpdateGuest);

function updateGuest(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new guest:';

        document.getElementById("GuestID").value = '';
        document.getElementById("GuestName").value = '';
        document.getElementById("GuestArrive").value = '';
        document.getElementById("GuestLeave").value = '';
        document.getElementById("VIP").value = '';

        document.getElementById("listDiv").style.display = 'none';
        document.getElementById("editDiv").style.display = 'block';

    } else {
        fetch('/Guest/list/' + id, {method: 'get'}
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

                document.getElementById("listDiv").style.display = 'none';
                document.getElementById("editDiv").style.display = 'block';

            }
        });
    }
}
function deleteGuest(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new guest:';

        document.getElementById("guestID").value = '';
        document.getElementById("guestName").value = '';
        document.getElementById("guestArrive").value = '';
        document.getElementById("guestLeave").value = '';
        document.getElementById("guestVIP").value = '';

        document.getElementById("listDiv").style.display = 'none';
        document.getElementById("editDiv").style.display = 'block';

    } else {
        fetch('/Guest/list/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(guest => {

            if (guest.hasOwnProperty('error')) {
                alert(guest.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + guest.name + ':';

                document.getElementById("guestID").value = id;
                document.getElementById("guestName").value = guest.name;
                document.getElementById("guestArrive").value = guest.Arrive;
                document.getElementById("guestLeave").value = guest.Leave;
                document.getElementById("guestVIP").value = guest.VIP;

                document.getElementById("listDiv").style.display = 'none';
                document.getElementById("editDiv").style.display = 'block';

            }
        });
    }
}

function saveEditGuest(event) {

    event.preventDefault();

    if (document.getElementById("GuestID").value.trim() === '') {
        alert("Please provide a guest ID.");
        return;
    }

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

    if (document.getElementById("VIP").value.trim() === '') {
        alert("Please provide whether a guest is a VIP or not.");
        return;
    }
    const id = document.getElementById("GuestID").value;
    const form = document.getElementById("GuestForm");
    const formData = new FormData(form);

    let apiPath = '/Guest/update';
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
            document.getElementById("listDiv").style.display = 'block';
            document.getElementById("editDiv").style.display = 'none';
            pageLoad();
        }
    });
}

function cancelEditGuest(event) {

    event.preventDefault();

    document.getElementById("listDiv").style.display = 'block';
    document.getElementById("editDiv").style.display = 'none';

}

function deleteGuest(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("id", id);

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