function pageLoad() {

    let guestHTML = '<table>' +
        '<tr>' +
        '<th>guestID</th>' +
        '<th>guestName</th>' +
        '<th>guestArrive</th>' +
        '<th>guestLeave</th>' +
        '<th>VIP</th>' +
        '<th class="last">Options</th>' +
        '</tr>';

    fetch('/guest/list', {method: 'get'}
    ).then(response => response.json()
    ).then(room => {
        for (let guest of guest) {

            guestHTML += `<tr>` +
                `<td>${guest.id}</td>` +
                `<td>${guest.Name}</td>` +
                `<td>${guest.Arrive}</td>` +
                `<td>${guest.Leave}</td>` +
                `<td>${guest.VIP}</td>` +
                `<td class="last">` +
                `<button class='editButton' data-id='${guest.id}'>Edit</button>` +
                `<button class='deleteButton' data-id='${guest.id}'>Delete</button>` +
                `</td>` +
                `</tr>`;

        }
    }
    guestHTML += '</table>';

    document.getElementById("listDiv").innerHTML = guestHTML;

    let editButtons = document.getElementsByClassName("updateButton");
    for (let button of editButtons) {
        button.addEventListener("click", updateGuest);
    }

    let deleteButtons = document.getElementsByClassName("deleteButton");
    for (let button of deleteButtons) {
        button.addEventListener("click", deleteGuest);
    }

};

document.getElementById("saveButton").addEventListener("click", saveUpdateFruit);
document.getElementById("cancelButton").addEventListener("click", cancelUpdateFruit);
}

function updateGuest(event) {

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
        fetch('/guest/get/' + id, {method: 'get'}
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
            fetch('/guest/get/' + id, {method: 'get'}
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

    if (document.getElementById("guestName").value.trim() === '') {
        alert("Please provide a guest name.");
        return;
    }

    if (document.getElementById("guestArrive").value.trim() === '') {
        alert("Please provide the guests date of arrival.");
        return;
    }

    if (document.getElementById("guestLeave").value.trim() === '') {
        alert("Please provide the guests date of departure.");
        return;
    }

    if (document.getElementById("guestVIP").value.trim() === '') {
        alert("Please provide whether a guest is a VIP or not.");
        return;
    }
    const id = document.getElementById("guestID").value;
    const form = document.getElementById("guestForm");
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