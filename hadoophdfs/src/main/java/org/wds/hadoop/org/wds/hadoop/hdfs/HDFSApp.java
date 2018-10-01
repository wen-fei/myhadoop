package org.wds.hadoop.org.wds.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;

public class HDFSApp {


    private static final String HDFS_PATH = "hdfs://223.3.92.88:8020";
    private Configuration configuration = null;
    private FileSystem fileSystem = null;

    /**
     * 创建目录
     *
     * @throws Exception
     */
    @Test
    public void mkdir() throws Exception {
        fileSystem.mkdirs(new Path("/hdfsapi/test/a"));
    }

    /**
     * 创建文件
     *
     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        FSDataOutputStream output = fileSystem.create(new Path("/hdfsapi/test/a.txt"));
        output.write("hello world".getBytes());
        output.flush();
        output.close();
    }

    /**
     * 查看文件内容
     *
     * @throws Exception
     */
    @Test
    public void cat() throws Exception {
        FSDataInputStream in = fileSystem.open(new Path("/hdfsapi/test/a.txt"));
        IOUtils.copyBytes(in, System.out, 1024);
        in.close();
    }

    /**
     * 修改文件名
     *
     * @throws Exception
     */
    @Test
    public void rename() throws Exception {
        Path oldName = new Path("/hdfsapi/test/a.txt");
        Path newName = new Path("/hdfsapi/test/b.txt");
        fileSystem.rename(oldName, newName);
    }

    /**
     * 查看文件
     * 如果通过hdfs shell的方式put上去的文件，那么才采用配置文件中的副本系数1
     * 如果通过java API上传，因为代码中没有手工配置系数，所以使用hadoop自己的副本系数3
     * @throws Exception
     */
    @Test
    public void listFiles() throws Exception {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/hdfsapi/test"));
        for (FileStatus fileStatus : fileStatuses) {
            String isDir = fileStatus.isDirectory() ? "文件夹" : "文件";
            short replication = fileStatus.getReplication();
            long len = fileStatus.getLen();
            String path = fileStatus.getPath().toString();
            System.out.println(isDir + "\t" + replication + "\t" + len + "\t" + path);
        }

    }

    @Test
    public void delete() throws IOException {
        fileSystem.delete(new Path("/hdfsapi/test/b.txt"), true);
    }

    @Test
    public void copyFromLocalFile() throws IOException {
        Path local = new Path("G:/GitHome/AngelEyes.7z");
        Path remote = new Path("/hdfsapi/test");
        fileSystem.copyFromLocalFile(local, remote);
    }

    /**
     * 带进度条
     *
     * @throws IOException
     */
    @Test
    public void copyFromLocalFileWithProgress() throws IOException {
        Path local = new Path("G:/GitHome/AngelEyes.7z");
        Path remote = new Path("/hdfsapi/test");
        fileSystem.copyFromLocalFile(local, remote);

        InputStream in = new BufferedInputStream(
                new FileInputStream(
                        new File("G:/GitHome/AngelEyes.7z")
                )
        );

        FSDataOutputStream output = fileSystem.create(new Path("/hdfsapi/test/ae.7z"), new Progressable() {
            @Override
            public void progress() {
                System.out.print("*");
            }
        });
        IOUtils.copyBytes(in, output, 2048);
    }


    @Before
    public void setUp() throws Exception {
        System.out.println("HDFSApp.setUp");
        configuration = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "stu");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("HDFSApp.tearDown");
        configuration = null;
        fileSystem = null;
    }

}
