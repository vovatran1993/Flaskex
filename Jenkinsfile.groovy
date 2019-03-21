{
properties([parameters([string(defaultValue: '127.0.0.1', description: 'Please give to build IP', name: 'IP', trim: true)])])
   
    stage("Install git"){
        sh "ssh    ec2-user@${IP}   sudo yum install git python-pip -y"
    }
    stage("Clone a repo"){
        git 'https://github.com/sstanytska/Flaskex.git'
    }
    stage("Install requirements"){
        sh "ssh ec2-user@${IP}   sudo pip install -r /tmp/requirements.txt"
    }
    stage("Run App"){
        sh "ssh ec2-user@${IP}    python /tmp/app.py"
    }
    stage("Copy files"){
        sh "scp  -r * ec2-user@${IP}:/tmp/"
    }
    stage("Move files to /flesk"){
        sh "ssh ec2-user@${IP}      sudo pip install -r /klask/requirements.txt"
    }
    stage("Move service to /etc"){
        sh "ssh ec2-user@${IP}      sudo /flaskex/flaskex.service /etc/system/system"
    }
    stage("Start service"){
        sh "ssh ssh ec2-user@${IP} sudo systemctl start flaskex"
    }
}