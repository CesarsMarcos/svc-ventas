pipeline {
    agent any

    environment {
        JAVA_HOME = '/opt/java/openjdk'   // O la ruta exacta si la sabes
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    tools {
        maven 'maven3'
    }

    stages {
        stage('Compilar Proyecto') {
            steps {
                sh 'java -version'
                sh 'mvn clean compile'
            }
        }
        stage('Ejecutar Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Resultados de Test') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }

    post {
        success {
            echo '✅ Compilación completada correctamente.'
        }
        failure {
            echo '❌ Falló la compilación. Revisa los errores del build.'
        }
    }
}
