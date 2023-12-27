//// problem.js
//async function submitCode(problemId) {
//    console.log("Button was pressed");
//    var code = document.getElementById('codeTextArea').value;
//    var url = `/problem/${problemId}/submit`;
//    console.log(code);
//    try{
//        const response = await fetch(url, {
//            method: 'POST',
//            headers: {
//                'Content-Type': 'application/json',
//                'Authorization': 'Bearer' + getCookie('token'),
//            },
//            body: JSON.stringify({ code: code}),
//        });
//
//        if(!response.ok){
//            throw new Error(`HTTP error! Status: ${response.status}, URL: ${response.url}`);
//        }
//
//        const data = await response.json();
//        console.log('Response', data);
//    } catch (error){
//        if (error.name === 'AbortError'){
//            console.log('Fetch Aborted');
//        } else {
//            console.error('Error', error);
//        }
//    }
//}

// problem.js

//function getCookie(name) {
//    const value = `; ${document.cookie}`;
//    console.log("the value is " + value);
//    const parts = value.split(`; ${name}=`);
//    console.log("the parts is " + parts);
//    if (parts.length === 2) return parts.pop().split(';').shift();
//}

function getCookie(name) {
    const cookies = document.cookie.split(';');
    console.log("cookies are " + cookies);
    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        // Check if this cookie starts with the name we're looking for
        if (cookie.startsWith(name + '=')) {
            // Return the part of the cookie value after the equal sign
            return cookie.substring(name.length + 7);
        }
    }

    // Return null if the cookie is not found
    return null;
}


async function submitCodeWrapper(problemId) {
    console.log("Before submitting code");

    // Additional logic before submitting code, if needed

    await submitCode(problemId);

    // Additional logic after submitting code, if needed

    console.log("After submitting code");
}

async function submitCode(problemId) {
    console.log("Button was pressed");
    var code = document.getElementById('codeTextArea').value;
    var url = `/problem-submit/${problemId}`;
    console.log(code);
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + getCookie('Authorization'),
            },
//            body: JSON.stringify({ code: code }),
            body: JSON.stringify(code),
        });
        console.log(getCookie('Authorization'));
        const data = await response.text();
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}, URL: ${response.url}`);
        }

//        const data = await response.json();

        console.log('Response', data);
    } catch (error) {
        if (error.name === 'AbortError') {
            console.log('Fetch Aborted');
        } else {
            console.error('Error', error);
        }
    }
}