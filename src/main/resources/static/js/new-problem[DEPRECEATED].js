async function submitCode(button){
    const url = 'http://127.0.0.1:59139/submissions?base64_encoded=false&fields=*';
    var code = document.getElementById('codeTextArea').value;
    const problemData = button.getAttribute('data-problem');
    console.log(problemData)
    // Extracting values using regular expression
    const example2InMatch = problemData.match(/example2_in=([^,]+)/);
    const example2OutMatch = problemData.match(/example2_out=([^,]+)/);
    const example3InMatch = problemData.match(/example3_in=([^,]+)/);
    const example3OutMatch = problemData.match(/example3_out=([^,)]+)/);
    // Check if matches are found and extract the values
    const example2InValue = example2InMatch ? example2InMatch[1] : null;
    const example2OutValue = example2OutMatch ? example2OutMatch[1] : null;
    const example3InValue = example3InMatch ? example3InMatch[1] : null;
    const example3OutValue = example3OutMatch ? example3OutMatch[1] : null;
    console.log("example2In:", example2InValue);
    console.log("example2Out:", example2OutValue);
    console.log("example3In:", example3InValue);
    console.log("example3Out:", example3OutValue);

    const options1 = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-RapidAPI-Key': 'f63ef82964msh09bccee0e458976p1ed017jsn113517e50100',
            'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
        },
        body: JSON.stringify({
            language_id: 75,
            source_code: code,
            stdin: example2InValue,
            expected_output: example2OutValue
        })
    };

    const options2 = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-RapidAPI-Key': 'f63ef82964msh09bccee0e458976p1ed017jsn113517e50100',
            'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
        },
        body: JSON.stringify({
            language_id: 75,
            source_code: code,
            stdin: example3InValue,
            expected_output: example3OutValue
        })
    };

    try {
        const response = await fetch(url, options1);
        const result = await response.json();
        const response2 = await fetch(url, options2);
        const result2 = await response2.json();
        const token = result.token;
        const token2 = result2.token;
        console.log("token 2 :", token);
        console.log("token 2 :", token2);
        document.getElementById('subcode').style.display = 'block';
        setTimeout(function() {
            document.getElementById('subcode').style.display = 'none';
        }, 1000); // Adjust the timeout as needed

        //showing the testcase result divs and hiding the example test case
        document.getElementById('exampleTestCase').style.display = "none";
        document.getElementById('hiddenTestCase1').style.display = "block";
        document.getElementById('hiddenTestCase2').style.display = "block";
        await checkSubmissionStatusSubmit(token, 1);
        await checkSubmissionStatusSubmit(token2, 2);
    } catch (error) {
        console.error(error);
    }
}

async function checkSubmissionStatusSubmit(token, testCase){
    const url = `http://127.0.0.1:52882/submissions/${token}?base64_encoded=false&fields=*`;
    const options = {
        method: 'GET',
        headers: {
            'X-RapidAPI-Key': 'f63ef82964msh09bccee0e458976p1ed017jsn113517e50100',
            'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
        }
    };
    try {
        var response = await fetch(url, options);
        var result = await response.json();
        while(result.status.id == 2 || result.status.id == 1){
            await new Promise(resolve => setTimeout(resolve, 1000));
            response = await fetch(url, options);
            result = await response.json();
            console.log(result.status.id);
        }
        console.log(result);
        if(result.status.id===3){
            if(testCase === 1){
                document.getElementById('hiddenTestCase1Heading').style.color= "green";
            }
            else{
                document.getElementById('hiddenTestCase2Heading').style.color= "green";
            }
        }
        else{
            if(testCase === 1){
                document.getElementById('hiddenTestCase1Heading').style.color= "red";
            }
            else{
                document.getElementById('hiddenTestCase2Heading').style.color= "red";
            }
        }
        updateResultsOnPage(result, testCase);
    } catch (error){
        console.error(error);
    }

    function updateResultsOnPage(result, testCase){

        var statusElement;
        var timeElement;
        var memoryElement;
        var languageElement;

        if(testCase === 1){
            statusElement = document.getElementById('status2');
            timeElement = document.getElementById('time2');
            memoryElement = document.getElementById('memory2');
            languageElement = document.getElementById('language2');
        }
        else{
            statusElement = document.getElementById('status3');
            timeElement = document.getElementById('time3');
            memoryElement = document.getElementById('memory3');
            languageElement = document.getElementById('language3');
        }

        statusElement.textContent = `Status: ${result.status.description}`;
        timeElement.textContent = `Time: ${result.time}`;
        memoryElement.textContent = `Memory: ${result.memory}`;
        languageElement.textContent = `Language used: ${result.language.name}`;
    }

}

async function runCode(button){
    const url = 'http://127.0.0.1:52882/submissions?base64_encoded=false&fields=*';
    var code = document.getElementById('codeTextArea').value;
    const problemData = button.getAttribute('data-problem');
    console.log(problemData)
    // Extracting values using regular expression
    const exampleInMatch = problemData.match(/exampleIn=([^,]+)/);
    const exampleOutMatch = problemData.match(/exampleOut=([^,]+)/);
    // Check if matches are found and extract the values
    const exampleInValue = exampleInMatch ? exampleInMatch[1] : null;
    const exampleOutValue = exampleOutMatch ? exampleOutMatch[1] : null;
    console.log("exampleIn:", exampleInValue);
    console.log("exampleOut:", exampleOutValue);
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-RapidAPI-Key': 'f63ef82964msh09bccee0e458976p1ed017jsn113517e50100',
            'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
        },
        body: JSON.stringify({
            language_id: 75,
            source_code: code,
            stdin: exampleInValue,
            expected_output: exampleOutValue
        })
    };

    try {
        const response = await fetch(url, options);
        const result = await response.json();
        const token = result.token;
        console.log(token);
        document.getElementById('subcode').style.display = 'block';
        setTimeout(function() {
            document.getElementById('subcode').style.display = 'none';
        }, 1000); // Adjust the timeout as needed

        //showing the example test case div
        document.getElementById('exampleTestCase').style.display = "block";
        document.getElementById('hiddenTestCase1').style.display = "none";
        document.getElementById('hiddenTestCase2').style.display = "none";
        await checkSubmissionStatus(token);
    } catch (error) {
        console.error(error);
    }
}

async function checkSubmissionStatus(token){
    const url = `http://127.0.0.1:52882/submissions/${token}?base64_encoded=false&fields=*`;
    const options = {
        method: 'GET',
        headers: {
            'X-RapidAPI-Key': 'f63ef82964msh09bccee0e458976p1ed017jsn113517e50100',
            'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
        }
    };
    try {
        var response = await fetch(url, options);
        var result = await response.json();
        while(result.status.id ==2 || result.status.id == 1){
            await new Promise(resolve => setTimeout(resolve, 1000));
            response = await fetch(url, options);
            result = await response.json();
            console.log(result.status.id);
        }
        console.log(result);
        if(result.status.id===3){
            document.getElementById('exampleTestCaseHeading').style.color= "green";
        }
        else{
            document.getElementById('exampleTestCaseHeading').style.color= "red";
        }
        updateResultsOnPage(result);
    } catch (error){
        console.error(error);
    }

    function updateResultsOnPage(result){

        const statusElement = document.getElementById('status');
        const stdinElement = document.getElementById('stdin');
        const outputElement = document.getElementById('output');
        const timeElement = document.getElementById('time');
        const memoryElement = document.getElementById('memory');
        const languageElement = document.getElementById('language');
        const compilationElement = document.getElementById('compile_output');

        statusElement.textContent = `Status: ${result.status.description}`;
        stdinElement.textContent = `Stdin: ${result.stdin}`;
        outputElement.textContent = `Stdout: ${result.stdout}`;
        timeElement.textContent = `Time: ${result.time}`;
        memoryElement.textContent = `Memory: ${result.memory}`;
        languageElement.textContent = `Language used: ${result.language.name}`;
        if(result.compile_output==null){
            compilationElement.style.display = "none";
        }
        else {
            compilationElement.style.display = "block";
            compilationElement.textContent = `Compilation message: ${result.compile_output}`;
        }
    }

}