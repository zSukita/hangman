# Jogo da Forca

Este é um simples jogo da forca desenvolvido em Java e gerenciado com o Apache Maven.

## Pré-requisitos

Para compilar e executar este projeto, você precisará ter instalado:

*   Java Development Kit (JDK) - Versão 17 ou superior
*   Apache Maven

## Como Compilar

Para compilar o projeto e baixar todas as dependências, navegue até o diretório raiz do projeto e execute o seguinte comando no seu terminal:

```bash
mvn clean compile
```

## Como Jogar

A palavra a ser adivinhada está definida diretamente no arquivo `pom.xml`. Para jogar com a palavra padrão ("Teste"), execute o comando:

```bash
mvn exec:java
```

### Alterando a Palavra

Para jogar com uma palavra diferente, você pode alterar o `pom.xml`:

1.  Abra o arquivo `pom.xml`.
2.  Encontre a seção `<configuration>` dentro do `exec-maven-plugin`.
3.  Altere o valor dentro da tag `<argument>` para a palavra que desejar.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <mainClass>br.com.dio.hangman.Main</mainClass>
                <arguments>
                    <!-- Altere a palavra aqui -->
                    <argument>SuaPalavra</argument>
                </arguments>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Após alterar o arquivo, basta rodar `mvn exec:java` novamente. 