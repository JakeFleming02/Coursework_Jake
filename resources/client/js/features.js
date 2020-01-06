function pageLoad() {

    let FeaturesHTML = `<table>` +
        '<tr>' +
        '<th>FeatureID</th>' +
        '<th>FeatureName</th>' +
        '<th class="last">Options</th>' +
        '</tr>';

    fetch('/Features/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Features => {

        for (let features of Features) {

            FeaturesHTML += `<tr>` +
                `<th>${features.FeatureID}</th>` +
                `<th>${features.FeatureName}</th>` +
                `<th><button class="editButton" data-id=${features.FeatureID}>Edit</button></th>` +
                `<th><button class='deleteButton' data-id='${features.FeatureID}'>Delete</button></th>` +
                `</tr>`;
        }

        FeaturesHTML += '</table>';

        document.getElementById("listDiv").innerHTML = FeaturesHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editFeatures);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteFeatures);
        }

    });

    document.getElementById("saveButton").addEventListener("click", saveEditFeatures);
    document.getElementById("cancelButton").addEventListener("click", clearEditFeatures);

    document.getElementById("editHeading").innerHTML = 'Add new features:';

}

function editFeatures(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new features:';

        document.getElementById("FeatureID").value = '';
        document.getElementById("FeatureName").value = '';
    } else {

        fetch('/Features/get/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(features => {

            if (features.hasOwnProperty('error')) {
                alert(features.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + features.FeatureName + ':';

                document.getElementById("FeatureID").value = features.FeatureID;
                document.getElementById("FeatureName").value = features.FeatureName;

            }

        });

    }

}

function saveEditFeatures(event) {

    event.preventDefault();

    if (document.getElementById("FeatureName").value.trim() === '') {
        alert("Please provide a features name.");
        return;
    }

    const id = document.getElementById("FeatureID").value;
    const form = document.getElementById("FeaturesForm");
    const formData = new FormData(form);

    let apiPath;
    if (id === '') {
        apiPath = '/Feature/new';
    } else {
        apiPath = '/Feature/update';
    }

    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            pageLoad();
            clearEditFeatures();
        }
    });
}

function clearEditFeatures(event) {

    document.getElementById("FeatureID").value = '';
    document.getElementById("FeatureName").value = '';

    document.getElementById("editHeading").innerHTML = 'Add new features:';

    if (event !== undefined && event !== null) {
        event.preventDefault();
    }

}

function deleteFeatures(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("FeatureID", id);

        fetch('/Features/delete', {method: 'post', body: formData}
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