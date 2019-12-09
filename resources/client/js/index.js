function pageLoad() {
    document.getElementById("GuestButton").addEventListener("click", guestRedirect);

    let RoomHTML = `<table>` +
        '<tr>' +
        '<th>RoomID</th>' +
        '<th>RoomName</th>' +
        '<th>RoomLocation</th>' +
        '<th>Cleaned</th>' +
        '<th>Checked</th>' +
        '<th>OutOfOrder</th>' +
        '</tr>';

    fetch('/Room/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Room => {

        for (let room of Room) {

            RoomHTML += `<tr>` +
                `<td>${room.RoomID}</td>` +
                `<td>${room.RoomName}</td>` +
                `<td>${room.RoomLocation}</td>` +
                `<td>${room.Cleaned}</td>` +
                `<td>${room.Checked}</td>` +
                `<td>${room.OutOfOrder}</td>` +
                `</tr>`;
        }

        RoomHTML += '</table>';

        document.getElementById("listDiv").innerHTML = RoomHTML;
})
}
function guestRedirect(){
    window.location.href="/client/guest.html"
}
