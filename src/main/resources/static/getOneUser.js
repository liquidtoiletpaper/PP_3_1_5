async function getOneUser(id) {
    let result;
    await fetch("adminApi/users")
        .then(res => res.json())
        .then(js => {
            for(let i = 0; i < js.length; i++) {
                if(id === js[i].id) {
                    result = js[i];
                }
            }
        })
    console.log(await result);
    return await result;
}