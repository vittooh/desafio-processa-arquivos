package filewatcher

import LinesProcessService
import ProcessResultData
import org.apache.commons.io.filefilter.NotFileFilter
import org.apache.commons.io.filefilter.SuffixFileFilter
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationObserver
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class ProcessFileWatcher {

    fun watchAndProcessFiles() {

        val observer = FileAlterationObserver(
            "arquivos", SuffixFileFilter(".dat")
                .and(NotFileFilter(SuffixFileFilter(".done.dat")))
        )
        val monitor = FileAlterationMonitor()
        val listener: FileAlterationListenerAdaptor = object : FileAlterationListenerAdaptor() {

            override fun onFileCreate(file: File) {
                //resolve de forma gulosa
                //melhorar a performance
                val resultData = LinesProcessService().processLines(
                    Files.readAllLines(Path.of(file.absolutePath))
                )
                showResults(resultData, file.name)
            }
        }

        observer.addListener(listener)
        monitor.addObserver(observer)
        monitor.start()
    }

    private fun showResults(resultData: ProcessResultData, fileName: String) {
        //pior-venda monetarios
        val linesToBeWritten = mutableListOf<String>()
        linesToBeWritten.add("Total Clients: ${resultData.client.size}")
        linesToBeWritten.add("Total Salesman: ${resultData.salesman.size}")
        linesToBeWritten.add("Most Higher Sale: ${resultData.mostHigherSale}")
        linesToBeWritten.add("Worst Sale: ${resultData.worstSale}")
        linesToBeWritten.add("Worst Salesman: ${resultData.worstSalesman}")
        linesToBeWritten.add("Best Salesman: ${resultData.bestSalesman}")
        Files.write(
            Paths.get(
                "./arquivos/" +
                        "${fileName.split(".")[0]}.done.dat"
            ), linesToBeWritten, Charset.defaultCharset()
        )


        //TODO criar teste unitário para essa parte

    }
}