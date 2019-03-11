package com.jw.ticket.spring.servlet;

import com.jw.ticket.spring.annotation.JWAutowired;
import com.jw.ticket.spring.annotation.JWController;
import com.jw.ticket.spring.annotation.JWRequestMapping;
import com.jw.ticket.spring.annotation.JWService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjunwen
 * @date 2018年11月02日11:56:16
 */
public class JWDispatcherServlet extends HttpServlet {

    /**
     * 存储配置信息
     */
    private Properties properties = new Properties();

    /**
     * 存储所有的类名
     */
    private List<String> classNames = new ArrayList<>();
    /**
     * 存储所有需要注入的对象
     */
    private Map<String, Object> ioc = new ConcurrentHashMap<>();

    /**
     * url对应方法
     */
    private Map<String, Method> handlerMappingMap = new ConcurrentHashMap<>();

    /**
     * url对用实例
     */
    private Map<String, Object> controllerMap = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("初始化加载DispatchServlet");
        // 1.加载配置文件
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        doLoadConfig(contextConfigLocation);

        // 2.初始化所有相关的类，扫描用户设定的包下面所有的类
        doScanner(properties.getProperty("scanPackage"));

        // 3.拿到扫描的类，通过反射机制，实例化，并放到ioc容器
        doInstance();

        // 4.注入依赖
        doIoc();

        // 5. 初始化HandlerMapping (将url和method对应上)
        initHandlerMapping();


    }

    /**
     * 加载配置文件
     *
     * @param localtion
     */
    public void doLoadConfig(String localtion) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(localtion);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != resourceAsStream) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 扫描所有的class
     *
     * @param packageName
     */
    public void doScanner(String packageName) {
        URL resource = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(resource.getFile());
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            // 判断是否是文件夹类型
            if (file.isDirectory()) {
                // 递归获取包
                doScanner(packageName + "." + fileName);
            } else {
                String className = packageName + "." + fileName.replaceAll(".class", "");
                classNames.add(className);
            }
        }
    }


    /**
     * 讲classNames的类实例化
     */
    public void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(JWController.class)) {
                    Object object = clazz.newInstance();
                    ioc.put(clazz.getSimpleName(), object);
                } else if (clazz.isAnnotationPresent(JWService.class)) {
                    JWService jwService = clazz.getAnnotation(JWService.class);
                    Object object = clazz.newInstance();
                    String beanName = jwService.value().trim();
                    if ("".equals(beanName)) {
                        beanName = clazz.getSimpleName();
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    System.out.println("====service beanName:" + beanName + "----object:" + object);
                    Class[] interfaces = clazz.getInterfaces();
                    for (Class<?> i : interfaces) {
                        System.out.println("service beanName:" + i.getName() + "----object:" + object);
                        ioc.put(i.getName(), instance);
                    }
                } else {
                    continue;
                }

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }

        }
    }


    /**
     * 依赖注入
     */
    public void doIoc() {
        if (ioc.isEmpty()) {
            System.out.println("占无实例化对象");
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(JWAutowired.class)) {
                    continue;
                }
                JWAutowired annotation = field.getAnnotation(JWAutowired.class);
                String beanName = annotation.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    final Object value = ioc.get(beanName);
                    System.out.println("zhu ru service benaName:" + beanName + "----object:" + value + "----fieldName:" + field.getName());
                    field.set(instance, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }

            }

        }
    }


    /**
     * 初始化HandlerMapping(将url和method对应上)
     */
    public void initHandlerMapping() {
        if (ioc.isEmpty()) {
            System.out.println("占无实例化对象");
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (!clazz.isAnnotationPresent(JWController.class)) {
                continue;
            }

            String baseUrl = "";
            if (clazz.isAnnotationPresent(JWRequestMapping.class)) {
                JWRequestMapping jwRequestMapping = clazz.getAnnotation(JWRequestMapping.class);
                baseUrl += jwRequestMapping.value();
            }

            for (Method method : clazz.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(JWRequestMapping.class)) {
                    continue;
                }
                JWRequestMapping methodAnnotation = method.getAnnotation(JWRequestMapping.class);
                String url = methodAnnotation.value();
                url = (baseUrl + "/" + url).replaceAll("/+", "/");
                handlerMappingMap.put(url, method);
                controllerMap.put(url, instance);
            }
        }

    }


    /**
     * 所有准备工作做完之后调用
     */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {

        if (handlerMappingMap.isEmpty()) {
            System.out.println("没有实例化url的方法");
            return;
        }
        final String requestURI = req.getRequestURI();
        final String contextPath = req.getContextPath();
        final String url = requestURI.replace(contextPath, "").replaceAll("/+", "/");
        System.out.println("url:" + url);
        resp.setCharacterEncoding("GB2312");
        if (!handlerMappingMap.containsKey(url)) {
            try {
                resp.getWriter().write("<h1>404 抱歉你访问的页面不存在！ </h1>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final Method method = handlerMappingMap.get(url);
        final Class<?>[] parameterTypes = method.getParameterTypes();


        Object values[] = new Object[parameterTypes.length];
        final Map<String, String[]> parameterMap = req.getParameterMap();


        for (int i = 0; i < parameterTypes.length; i++) {
            final String paramTypeName = parameterTypes[i].getSimpleName();
            if (paramTypeName.equals("HttpServletRequest")) {
                values[i] = req;
                continue;
            }

            if (paramTypeName.equals("HttpServletResponse")) {
                values[i] = resp;
                continue;
            }

            if (paramTypeName.equals("String")) {
                values[i] = "";
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String value = Arrays.toString(entry.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                    values[i] = value;
                }
            }
        }
        method.setAccessible(true);
        try {
            final Object obj = controllerMap.get(url);
            method.invoke(obj, values);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
