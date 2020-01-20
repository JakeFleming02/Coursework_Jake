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
                `<td style="background-color: ${"#A6C1FF"}">${staff.StaffID}</td>` +
                `<td style="background-color: ${"#A6C1FF"}">${staff.StaffName}</td>` +
                `<td style="background-color: ${"#A6C1FF"}"><button class="editButton" data-id=${staff.StaffID}>Edit</button></td>` +
                `<td style="background-color: ${"#A6C1FF"}"><button class='deleteButton' data-id='${staff.StaffID}'>Delete</button></td>` +
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