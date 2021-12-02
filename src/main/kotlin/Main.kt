package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {

    /**
     * 4.3.1
     */
    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()

    /**
     * 4.3.2
     */
    fun obtenerCiudadesDeClientes(): Set<Ciudad> = clientes.map { it.ciudad }.toSet()

    fun obtenerClientesPor(ciudad:Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad }

    /**
     * 4.3.3
     */
    fun checkTodosClientesSonDe(ciudad : Ciudad): Boolean = clientes.all { it.ciudad == ciudad }

    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any { it.ciudad == ciudad }

    fun cuentaClientesDe(ciudad: Ciudad): Int = clientes.count { it.ciudad == ciudad }

    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }

    /**
     * 4.3.4
     */
    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedByDescending { it.pedidos.size }

    /**
     * 4.3.5
     */
    fun obtenerClientesConPedidosSinEntregar(): Set<Clientes> = clientes.partition { clientes -> clientes.pedidos.count { it.estaEntregado } < clientes.pedidos.count { !it.estaEntregado } }.second.toSet()

    /**
     * 4.3.6
     */
    fun obtenerProductosPedidos(): Set<Producto> = clientes.flatMap { it.obtenerProductosPedidos() }.toSet()

    /**
     * 4.3.7
     */
    fun obtenerProductosPedidosPorTodos(): Set<Producto> = clientes.fold(obtenerProductosPedidos()) { acc, cliente ->
        acc.intersect(cliente.pedidos.flatMap { it.productos }.toSet())
    }

    /**
     * 4.3.8.1
     */
    fun obtenerNumeroVecesProductoPedido(producto: Producto): Int = obtenerProductosPedidos().count { it == producto }

    /**
     * 4.3.9
     */
    fun agrupaClientesPorCiudad(): Map<Ciudad, List<Clientes>> = clientes.groupBy { it.ciudad }

    /**
     * 4.3.10
     */
    fun mapeaNombreACliente(): Map<String, Clientes> = clientes.associateBy { it.nombre }
    fun mapeaClienteACiudad(): Map<Clientes, Ciudad> = clientes.associateWith { it.ciudad }
    fun mapeaNombreClienteACiudad(): Map<String, Ciudad> = clientes.associate { Pair(it.nombre, it.ciudad) }

    /**
     * 4.3.11
     */
    fun obtenerClienteConMaxPedidos(): Clientes? = clientes.maxByOrNull { it.pedidos.size }
}

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"

    /**
     * 4.3.6
     */
    fun obtenerProductosPedidos(): List<Producto> = pedidos.flatMap { it.productos }

    /**
     * 4.3.8
     */
    fun encuentraProductoMasCaro(): Producto? = obtenerProductosPedidos().sortedBy { it.precio }[0]
    //fun encuentraProductoMasCaro(): Producto? =
    //        pedidos.filter { it.estaEntregado }.flatMap { it.productos }.maxByOrNull { it.precio }

    /**
     * 4.3.11
     */
    fun obtenerProductoMasCaroPedido(): Producto? = pedidos.flatMap { it.productos }.maxByOrNull { it.precio }

    /**
     * 4.3.12
     */
    fun dineroGastado(): Double = pedidos.flatMap { it.productos }.sumOf { it.precio }
}

data class Pedido(val productos: List<Producto>, val estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}

fun main() {
    val ciudad1 = Ciudad("Cádiz")
    val producto1 = Producto("Producto_1",24.99)
    val pedido1 = Pedido(listOf(producto1),false)
    val cliente1 = Clientes("Juan López", ciudad1, listOf(pedido1))
    val tienda1 = Tienda("tienda1", listOf(cliente1))

    val ciudad2 = Ciudad("Barcelona")
    val producto2 = Producto("Producto_2",245.99)
    val pedido2 = Pedido(listOf(producto2),false)
    val cliente2 = Clientes("Pedro Jiménez", ciudad1, listOf(pedido2))
    val tienda2 = Tienda("tienda2", listOf(cliente2))

    println("Conjunto de clientes: ${tienda1.obtenerConjuntoDeClientes()}")
    println("Ciudades de los clientes: ${tienda1.obtenerCiudadesDeClientes()}")
    println("Clientes de la ciudad $ciudad1: ${tienda1.obtenerClientesPor(ciudad1)}")
    println("¿Son todos los clientes de $ciudad2?: ${tienda1.checkTodosClientesSonDe(ciudad2)}")
    println("¿Hay algún cliente de $ciudad1?: ${tienda1.hayClientesDe(ciudad1)}")
    println("¿Hay algún cliente de $ciudad2?: ${tienda1.hayClientesDe(ciudad2)}")
    println("Número de clientes de $ciudad1: ${tienda1.cuentaClientesDe(ciudad1)}")
    println("Número de clientes de $ciudad2: ${tienda1.cuentaClientesDe(ciudad2)}")
    println("¿Que clientes son de $ciudad1? ${tienda1.encuentraClienteDe(ciudad1)}")
    println("¿Que clientes son de $ciudad2? ${tienda1.encuentraClienteDe(ciudad2)}")
    println("Lista descendiente de clientes por pedidos realizados: ${tienda1.obtenerClientesOrdenadosPorPedidos()}")
    println("Lista de clientes con pedidos sin entregar: ${tienda1.obtenerClientesConPedidosSinEntregar()}")

}