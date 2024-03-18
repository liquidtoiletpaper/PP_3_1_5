'use strict';

function roles2(roles3) {
    let roles = roles3[0].name.replace('ROLE_', '');
    if(roles3.length === 2) {
        roles = roles + ", " + roles3[1].name.replace('ROLE_', '');
    }
    return roles
}

function getCurrentUser() {
    fetch("userApi/auth")
        .then(res => res.json())
        .then(user => {
            const roles = user.roles.map(role => role.role).join(',')
            $('#emailCurrentUser').append(`<span>${user.username}</span>`)
            $('#roleCurrentUser').append(`<span>${roles2(user.roles)}</span>`)
            const u = `$(
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.lastName}</td>
                        <td>${user.firstName}</td>
                        <td>${roles2(user.roles)}</td>
                    </tr>)`;
            $('#oneUser').append(u)
        })
}

getCurrentUser()