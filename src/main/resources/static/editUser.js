let formEdit = document.forms["formEdit"];
editUser();

async function editModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#edit'));
    await openAndFillInTheModal(formEdit, modal, id);
    formEdit.roles.options[1].selected=true;
    formEdit.password.value;
}

function editUser() {
    formEdit.addEventListener("submit", ev => {
        ev.preventDefault();
        let roles = [];
        // roles.push({id: "1", name: "ROLE_ADMIN"});
        // roles.push({id:"2", name: "ROLE_ADMIN"});
        for (let i = 0; i < formEdit.roles.options.length; i++) {
            if (formEdit.roles.options[i].selected) {
                roles.push({id: formEdit.roles.options[i].value, name: "ROLE_" + formEdit.roles.options[i].text});
            }
            console.log(roles);

        }
        // formEdit.roles.options[1].
        // console.log(formEdit.roles);
        fetch("adminApi/user/" + formEdit.id.value, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: formEdit.id.value,
                username: formEdit.username.value,
                firstName: formEdit.firstname.value,
                lastName: formEdit.lastname.value,
                password: formEdit.password.value,
                roles:roles
            })
        }).then(() => {
            $('#closeEdit').click();
            getTableUser()
        });
    });
}
