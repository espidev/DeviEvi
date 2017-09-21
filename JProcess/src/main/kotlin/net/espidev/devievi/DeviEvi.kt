package net.espidev.devievi

object DeviEvi{
    val version = "1.0.0"

    fun println(output: String){
        System.out.println(output);
    }
}


fun println(output: String){
    DeviEvi.println(output);
}

fun main(args: Array<String>){
    System.out.println("Starting DeviEvi v${DeviEvi.version}...")
}

fun createTrack(){

}