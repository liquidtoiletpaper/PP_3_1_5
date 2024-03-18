'use strict';
const tbody = $('#AllUsers');
getTableUser();

let roles3 = [];
function roles2(roles3) {
    let roles = roles3[0].name.replace('ROLE_', '');
    if(roles3.length === 2) {
        roles = roles + ", " + roles3[1].name.replace('ROLE_', '');
    }
    return roles
}
function getTableUser() {
    tbody.empty();
    fetch("adminApi/users")
        .then(res => res.json())
        .then(js => {
            console.log(js);
            js.forEach(user => {

                // const roles = user.roles.map(role => role.role).join(',');
                const users = $(
                    `<tr>
                        <td class="pt-3" id="userID">${user.id}</td>
                        <td class="pt-3" >${user.username}</td>
                        <td class="pt-3" >${user.firstName}</td>
                        <td class="pt-3" >${user.lastName}</td>
                        <td class="pt-3" >${roles2(user.roles)}</td>
                        
                        <td>
                            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#edit" onclick="editModal(${user.id})">
                            Edit
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete" onclick="deleteModal(${user.id})">
                                Delete
                            </button>
                        </td>
                    </tr>`
                );
                tbody.append(users);
            });
        })
}
