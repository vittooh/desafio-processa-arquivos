import filewatcher.ProcessFileWatcher

fun main(args: Array<String>) {

    ProcessFileWatcher().watchAndProcessFiles()

    val inputs = listOf(
        "001ç1234567891234çPedroç50000",
        "001ç3245678865434çPauloç40000.99",
        "002ç2345675434544345çJose da SilvaçRural",
        "002ç2345675433444345çEduardo PereiraçRural",
        "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro", //199
        "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo", //393.50
        "003ç11ç[1-10-100,2-30-2.50,3-40-3.10]çPedro", //199
        "003ç12ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo", //393.50
        "003ç13ç[1-34-10,2-33-1.50,3-40-0.10]çDBL", //393.50
    )

    val linesProcessService = LinesProcessService()
    val result = linesProcessService.processLines(
        inputs
    )


}