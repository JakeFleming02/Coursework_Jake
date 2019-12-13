function pageLoad() {
    document.getElementById("IndexButton").addEventListener("click", indexRedirect);

document.getElementById("saveButton").addEventListener("click", saveEditGuest);
document.getElementById("cancelButton").addEventListener("click", cancelEditGuest);

    function editGuest(event) {

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

            fetch('/Guest/get/' + id, {method: 'get'}
            ).then(response => response.json()
            ).then(guest => {

                if (guest.hasOwnProperty('error')) {
                    alert(guest.error);
                } else {

                    document.getElementById("editHeading").innerHTML = 'Editing ' + guest.GuestName + ':';

                    document.getElementById("GuestID").value = id;
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

    if (document.getElementById("VIP").value.trim() === '') {
        alert("Please provide whether a guest is a VIP or not.");
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

let GuestHTML = `<table>` +
    '<tr>' +
    '<th>GuestID</th>' +
    '<th>GuestName</th>' +
    '<th>GuestArrive</th>' +
    '<th>GuestLeave</th>' +
    '<th>VIP</th>' +
    '<th>Options</th>' +
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
            `<td><button class="editButton" data-id=${guest.GuestID}>Edit</button></td>` +
            `</tr>`;
    }

    GuestHTML += '</table>';

    document.getElementById("listDiv").innerHTML = GuestHTML;

});

function indexRedirect(){
    window.location.href="/client/index.html"
}}