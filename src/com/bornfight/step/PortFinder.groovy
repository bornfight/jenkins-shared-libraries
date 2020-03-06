package com.bornfight.step

class PortFinder {
    private static final int MAX_PORT_NUMBER = 1025
    private static final int MIN_PORT_NUMBER = 65535

    public int getAvailablePort(){
        this.getAvailablePort(MIN_PORT_NUMBER, MAX_PORT_NUMBER)
    }

    public int getAvailablePort(int upper) {
        this.getAvailablePort(MIN_PORT_NUMBER, upper)
    }

    public int getAvailablePort(int lower, int upper){
        if (lower < MIN_PORT_NUMBER || upper > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid port range ")
        }

        Random rand = new Random()
        while(true){
            int port = rand.nextInt(upper-lower) + lower
            ServerSocket ss = null
            DatagramSocket ds = null
            try {
                ss = new ServerSocket(port)
                ss.setReuseAddress(true)
                ds = new DatagramSocket(port)
                ds.setReuseAddress(true)
                return port
            } catch (IOException ignored) {
            } finally {
                if (ds != null) {
                    ds.close()
                }

                if (ss != null) {
                    try {
                        ss.close()
                    } catch (IOException ignored) {}
                }
            }
        }
    }
}
