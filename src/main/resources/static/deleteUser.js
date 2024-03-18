'use strict';

let deleteForm = document.forms["formDelete"]
let myid;
deleteUser();


async function deleteModal(id) {
    myid = id;
    console.log(id);
    console.log("nachalo");
    const modal = new bootstrap.Modal(document.querySelector('#delete'));
    await openAndFillInTheModal(deleteForm, modal, id);
    switch (deleteForm.roles.value) {
        case '1':
            deleteForm.roles.value = 'ADMIN';
            break;
        case '2':
            deleteForm.roles.value = 'USER';
            break;
    }

    await deleteUser()
}

function deleteUser() {
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        console.log("vivod delita");
        console.log(myid);
        fetch("adminApi/user/" + myid, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            $('#closeDelete').click();
            getTableUser();
        });
    });
}