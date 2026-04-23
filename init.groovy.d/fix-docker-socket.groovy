import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.PosixFilePermissions

def socket = new File("/var/run/docker.sock")
if (socket.exists()) {
    ["chmod", "666", "/var/run/docker.sock"].execute().waitFor()
    println "Docker socket permissions fixed"
}