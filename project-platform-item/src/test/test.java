import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author kongling
 * @package PACKAGE_NAME
 * @date 2019-10-28  11:17
 * @project wisdom-party-cloud
 */
public class test {

    private static RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
                Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
                rowlist.removeAll(Lists.newArrayList(0,1,2,3));

                //通过工具类创建writer
                ExcelWriter writer = ExcelUtil.getWriter("C:\\Users\\Administrator\\Desktop\\writeTest.xlsx");
                //通过构造方法创建writer
                //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

                //跳过当前行，既第一行，非必须，在此演示用
                // writer.passCurrentRow();

                //合并单元格后的标题行，使用默认标题样式
                // writer.merge(row1.size() - 1, "测试标题");
                //一次性写出内容，强制输出标题
                writer.write(rowlist, true);
                //关闭writer，释放内存
                writer.close();
            }
        };
    }

    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader("E:\\work\\梦之源\\文档\\项目文档\\智慧党建\\需导入数据(1).xlsx",2);
        List<List<Object>> readAll = reader.read();

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("C:\\Users\\Administrator\\Desktop\\writeTest.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        // writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        // writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容，强制输出标题
        writer.write(readAll, true);
        //关闭writer，释放内存
        writer.close();
    }


}
