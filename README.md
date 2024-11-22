# Aventuras de Aya

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.
- `html`: Web platform using GWT and WebGL. Supports only Java projects.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `html:dist`: compiles GWT sources. The compiled application can be found at `html/build/dist`: you can use any HTTP server to deploy it.
- `html:superDev`: compiles GWT sources and runs the application in SuperDev mode. It will be available at [localhost:8080/html](http://localhost:8080/html). Use only during development.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

### **História: Aya e o Resgate dos Gatinhos**

**Aya** é uma gatinha aventureira que vive em uma cidade repleta de belos pontos turísticos. Certo dia, ao explorar o bairro, ela percebe algo estranho: seus amigos gatinhos estão desaparecendo um a um. Depois de investigar, ela descobre o culpado: **Spike,** um cachorro ressentido que acredita que os gatos estão roubando toda a atenção e carinho que antes eram dele.

**Spike** fazia parte de uma família que tinha tanto ele quanto um gato. Com o tempo, ele começou a sentir que o gato recebia mais atenção da família. Consumido por ciúmes e mágoa, Spike decidiu se vingar não apenas daquele gato, mas de todos os gatos da cidade. Ele os capturou e os prendeu nos pontos turísticos, tornando cada local um "desafio" para qualquer um que tentasse resgatá-los.

Agora, **Aya** precisa usar sua inteligência e coragem para libertar os gatinhos e mostrar a **Spike** que atenção e amor não precisam ser exclusivos.

---

### **Estrutura da Narrativa**

### **1. Os Pontos Turísticos**

Aya descobre que Spike prendeu os gatos em quatro locais famosos da cidade:

- **Geoparque Quarta Colônia**
- **Jardim Botânico da UFSM**
- **Vila Belga**
- **Catedral Metropolitana de Santa Maria**

Cada local tem um desafio, como quizzes ou puzzles temáticos, que Aya precisa resolver para libertar os gatinhos.

### **2. O Esconderijo de Spike**

Após libertar os gatinhos, Aya segue a trilha de Spike até seu esconderijo. Lá, ela enfrenta o vilão em um desafio final: um mix de perguntas e puzzles que testam tudo o que Aya aprendeu sobre os pontos turísticos.

### **3. O Desfecho**

Após vencer o desafio, Aya confronta Spike. Ele finalmente admite que se sentia deixado de lado e só queria ser notado novamente. Aya o convence de que carinho e atenção podem ser compartilhados e que ele pode se redimir ajudando a proteger os pontos turísticos e os gatinhos.

Spike, arrependido, liberta os gatinhos restantes e promete ser um protetor da cidade, trabalhando ao lado de **Aya** para torná-la ainda mais especial.

---

### **Gameplay**

1. **Exploração:**
    
    Aya percorre o mapa, indo de um ponto turístico ao outro, com uma trilha pontilhada indicando seu caminho.
    
2. **Quizzes/Puzzles:**
    
    Cada local contém um quiz ou puzzle relacionado ao tema do ponto turístico. Por exemplo:
    
    - No Jardim Botânico, Aya pode ter que identificar tipos de plantas ou flores.
    - Na Vila Belga, um puzzle de formas e cores para libertar um gatinho.
3. **Confronto Final:**
    
    O desafio final no esconderijo de Maximus é uma combinação de perguntas temáticas e um puzzle mais elaborado, com um tempo limite para completar.
    
4. **Recompensa:**
    
    Ao resgatar todos os gatinhos, Aya recebe a gratidão deles, e Maximus se redime, prometendo nunca mais agir por ciúmes.
