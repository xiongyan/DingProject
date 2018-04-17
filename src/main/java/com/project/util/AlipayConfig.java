package com.project.util;

/**
 * Created by laishun on 2018/3/16.
 */
public class AlipayConfig {

    //合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String partner = "2088212007368425";

    //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
    public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCYFLGelTFEWUYnDlBCaqh8rqZwiDBbB3pii3MrpZ/w8QCDDfO4NOv/5Fkx6vmpFBVnXi7MPZt3eb5OSLRmkfTL378nGsXxfMGFRzNmWjjrCgmiZEpXHfLbIi/TUI/3cFl9PPYHjYOKiUZj0HilqQEhGlaLunCZGpZOColE0kcWUMEywbe3aU5JUwAXdrxvld7ALSYd2uCWoF8qcozHSu82YD2yBhc0/VMR8scNwA+lWrn0v1pcbBxop2AGILm0lXjEOAuZVqeQ/9um2H/9yQrZchbstm7+UAQAllfNjeKu1aO+RNPSBDGMGj3DbUWlvuoJL8h5YkLIQhwArGei7sZhAgMBAAECggEAWRSmBfH17cctJGtp771x+7aBn6DmUivLMDXmvEZDrWo9loZxoBC/vCARbv/oRXWSI/4zA0eC7TTOmCRWJ06PSWxCF7HDCHS/FkZigS2P8Xzrr8d3FBhizKD1yp2B9rpEcOtoYXwu9NL7/DN8t+yK0M0QrMAEfGnqaMQbK8jQAu1ZTLokZKebB+PQm/ERY/XYJxUxj5K0DvQaLzrGwWhGxkfj5k7MZr0gLQTMFs7jODWRH6FEWwsdjm3cm1z5Dq3dbBgGLYY4uuAW7WdMW5fjumvvSFH+j6ShpmTzhgCQa1pMu0W3rBIhwM1u07pJI4mW3kOhLsX6l8/L5hMbeoTf0QKBgQDhSpuH2o5Ec4znnIOjwDiva7KeLVWKT6wbDCnaGSeFv2tSmink/uAFKpD35a0ml6dpTSPnzxDWZoh32uDRCB41h6HY8AH5LOqXeWgaU7CKKsfiJ2VvZHlJ0LLdmmHo7vb7fehVoUTV1fDksto/OYW80VGgTK78jK31AEAcwCCSBwKBgQCsz3OrnbdE80HkF5nHlg31mV8GWOX7prCOrewmLogVDEP+w8M11DT/u1XMThDemlo9A5ESLF6oPfTmdB1+zYzQvAe8/Nj5ZMw+rCg6wHKdh3tGAElL/Lp4R5XXHavQDpiigXw+Q+NbeoWKCEVeTgJRGNrThNA7+kMrQdY1ZtgqVwKBgQCZsVEbRAFMN1wH4aF4g7joC1vGRv0VJzctGIYwsHpi5uxrZJpyC2J+g8R0KW6PZmQiFSsKiBtfeIFe6qX2VEhvkkDagxmFiJvdjEh1tWQ5Teggo2myrWOE9Ogg/6INy4AYn5NxX1xbWOFG07DJ8w8GefHqnoW96qb9/ha+xl8x3wKBgCIWyjqQYha35ceY4Dz+3MIV/yjBYJzDxRBgPrVJeI6uXLtk4p07vzizO7mRrMI8sJBYo8Vx+zo0/pp3TVo2MhD7BovBVQvdVm0GiOkODY4Fq8ujQtI6iI4xkN1efQYN2w4zfM7LlY32HWGdQBHaUZqtzHztNiaet6FQ23CtWPcBAoGANYZvz2kv3Bw12Nb5ldOxkYrSzLtat1f9lZEzJ3Z3kDvt6D6YYWH7WIkVNFdDSXX0FfG+E5fprVL8Ayhd/Ai3//P5JdCXcbJxa6ScDnYwGS5TbtgY/SfAIRkvxHn0gy3DX43hi0Iiya0j4rvVOhla6sn5GHCFqR0nISfQIEUXRjo=";

    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String alipay_public_key  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqpM66PoMJoEbXgZ9rhM0RLHab5eGEvUUewH/FNV8VairvR7bTrTCIRm5peCDNukQsWqU5zq96Ao8XnB8lJIgSzLQc/qsvqhWm87/05xqwoDkc/WTf8AnSXKaQAiXu508OqkgvUvcV1ysNG4Ckw/SJgRhCAVi6B3AbgwWFQE5f2Nndt0Ye3uCWPhrbXZvtd6b2wDoot4iKn3Ag3zHCCuRBeW5vLLaSobgX019sgohQ2ZQdEDdjX1QnQLAh9uZSAOwggaLGN235jc6yJAuHKwQpexJvLP/tB28/CSMqVtyAWCyKMpUdrF1nKHKYmzFj2a8UGUak799s25ZCkE4Mlt4zQIDAQAB";

    // 签名方式
    public static String sign_type = "RSA2";

    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
    public static String log_path ="C://";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";

    // 接收通知的接口名
    public static String service = "http://localhost:8080/charge";
    //public static String service = "mobile.securitypay.pay";

    //APPID
    public static String app_id="2016091500515498";

    //gateway
    //public static String gateway = "https://openapi.alipay.com/gateway.do";
    public static String gateway = "https://openapi.alipaydev.com/gateway.do";//沙箱测试固定

}
