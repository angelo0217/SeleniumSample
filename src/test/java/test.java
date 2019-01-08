def remote=[:]
        remote.name="node-1"
        remote.host="${dockerIp}"
        remote.allowAnyHosts=true

        def testBasic(inUrl,reqData,validResStr){
        response=httpRequest(consoleLogResponseBody:true,
        contentType:'APPLICATION_JSON',
        httpMode:'POST',
        connectionTimeout:60,
        requestBody:"${reqData}",
        url:"${inUrl}",
        validResponseCodes:'200',
        validResponseContent:"${validResStr}")
        return response
        }
        node{
        stage('git clone'){
        cleanWs()
        withCredentials([usernamePassword(credentialsId:'deanGitLink',passwordVariable:'GIT_PASSWORD',usernameVariable:'GIT_USERNAME')]){
        withEnv(['PATH+EXTRA=/usr/sbin:/usr/bin:/sbin:/bin']){
        sh'echo ${GIT_USERNAME}'
        sh'git clone --single-branch --branch master https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/angelo0217/Springboot2Demo.git'
        //sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@<REPO> --tags')
        }
        }
        }
        stage('maven build'){
        withEnv(['PATH+EXTRA=/usr/sbin:/usr/bin:/sbin:/bin']){
        sh'cd Springboot2Demo'
        dir('Springboot2Demo'){
        sh'pwd'
        sh'mvn clean package -Dmaven.test.skip=true'
        }
        }
        }

        withCredentials([usernamePassword(credentialsId:'MainMachine',passwordVariable:'password',usernameVariable:'user')]){
        remote.user=user
        remote.password=password
        stage('shutdown test docker'){
        try{
        sshCommand remote:remote,command:'docker stop webTest && docker rm web && docker rmi demo/webTest && cd /opt/vms/ && rm -rf *';
        }catch(exc){
        echo'shutdown docker error'
        }
        }
        stage('build test docker'){
        //sshCommand remote: remote, command: 'for i in {1..5}; do echo -n \"Loop \$i \"; date ; sleep 1; done'
        sshPut remote:remote,from:'/var/jenkins_home/workspace/testPPLhttp/Springboot2Demo/target/demo-0.0.1-SNAPSHOT.war',into:'/opt/vms'
        sshPut remote:remote,from:'/var/jenkins_home/workspace/testPPLhttp/Springboot2Demo/doc/Dockerfile',into:'/opt/vms'

        sshCommand remote:remote,command:'cd /opt/vms/ && docker build -t="demo/webTest" .'
        sshCommand remote:remote,command:'docker run -itd -v /opt/vms/log:/workspace/logs --name web -d -p 10000:8080 demo/web'

        try{
        sshCommand remote:remote,command:'docker stop web && docker rm webTest && docker rmi demo/webTest';
        }catch(exc){
        echo'shutdown docker error'
        }
        }

        }

        stage('TestWeb'){
        testBasic("${testUrl}",
        """{
        \"serverIp\" : \"${webIpPort}\",
        \"range\":\"${range}\",
        \"name\":\"Jenkins\",
        \"age\":13
        }""",
        "\"msg\":\"success\"")
        }


        withCredentials([usernamePassword(credentialsId:'MainMachine',passwordVariable:'password',usernameVariable:'user')]){
        remote.user=user
        remote.password=password
        stage('shutdown prod docker'){
        try{
        sshCommand remote:remote,command:'docker stop web && docker rm web && docker rmi demo/web && cd /opt/vms/ && rm -rf *';
        }catch(exc){
        echo'shutdown docker error'
        }
        }
        stage('build prod docker'){
        sshPut remote:remote,from:'/var/jenkins_home/workspace/testPPLhttp/Springboot2Demo/target/demo-0.0.1-SNAPSHOT.war',into:'/opt/vms'
        sshPut remote:remote,from:'/var/jenkins_home/workspace/testPPLhttp/Springboot2Demo/doc/Dockerfile',into:'/opt/vms'
        //sshCommand remote: remote, command: 'for i in {1..5}; do echo -n \"Loop \$i \"; date ; sleep 1; done'
        sshCommand remote:remote,command:'cd /opt/vms/ && docker build -t="demo/web" .'
        sshCommand remote:remote,command:'docker run -itd -v /opt/vms/log:/workspace/logs --name web -d -p 12345:8080 demo/web'
        }
        }
        }
