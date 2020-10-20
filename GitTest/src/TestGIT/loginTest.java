package TestGIT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class loginTest {
		public static WebDriver driver;
		public static JavascriptExecutor jse;
		public static String proyecto;
			static {
			driver=getDriver();
			jse=getJse();
			}
			public static WebDriver getDriver() {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mario\\Downloads\\test_automation\\drivers\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			return driver;
			}
			public static JavascriptExecutor getJse() {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			return jse;
			}
			public static void main(String[] args)  throws InterruptedException{
			try {	
				String url=null,user = null,pass = null, empresa=null, rs=null, resultado=null;
				String exito="FLUJO CONCLUIDO EXITOSAMENTE";
				String datos[];
				datos = new String[12];
				loginTest puente = new loginTest ();
				puente.datos2(datos);
				for(int a=0;a<datos.length;a++) {
					System.out.println(datos[a]);
				}
				url=datos[1];
				user=datos[3];
				pass=datos[5];
				empresa=datos[7];
				rs=datos[9];
				resultado=datos[11];
				driver.get(url);
				driver.manage().window().maximize();
				Thread.sleep(2000);
				//Login
				System.out.println("LOGIN::::::::::::::: PANTALLA DE LOGIN");
				puente.login(empresa, user, pass,rs,url,resultado,exito);
				puente.correo(url,exito);
				//driver.quit();
				}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Se cerro el navegador por Exception");
				//driver.quit();
				}
			}
			public static void datos2(String datos[]){
				try {
					String projectPaht=System.getProperty("user.dir");
					String ruta=projectPaht+"\\Excel\\loginHS.xlsx";
					FileInputStream f= new FileInputStream(ruta);
					XSSFWorkbook libro= new XSSFWorkbook (f);
					XSSFSheet hoja= libro.getSheetAt(0);
					Iterator <Row> filas=hoja.iterator();
					Iterator <Cell> celdas;
					Row fila;
					Cell celda;
					int y=0;
					System.out.println(datos.length);
					DataFormatter formatter = new DataFormatter();
					while(filas.hasNext()) {
						fila=filas.next();
						celdas=fila.cellIterator();
						while(celdas.hasNext()) {
							celda=celdas.next();
							String contenidoCelda = formatter.formatCellValue(celda);
							datos[y]=contenidoCelda;
							y++;
							}
						}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return;
			}
			public static void login(String empresa, String user, String pass, String rs,String url, String resultado, String exito) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException{
				String newURL=null;
				String alerta=null;
				File Archivo;
				PrintWriter Escribir;
				String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
				Archivo =new File (resultado+timestamp+".doc");
				try {
				driver.findElement(By.id("vMB_EPR_COD")).sendKeys(empresa);
				Thread.sleep(3000);
				System.out.println("LOG::::::::::: SE INGRESO LA EMPRESA");
				//Validar nombre de la empresa
				driver.findElement(By.id("IMAGE2")).click();
				Thread.sleep(1000);
				alerta=driver.switchTo().alert().getText();
				System.out.println("Algo ha salido mal");
				System.out.println(alerta);
				Escribir=new PrintWriter (Archivo,"utf-8");
				Escribir.println(alerta);
				Escribir.close();
				driver.switchTo().alert().accept();
				System.out.println("Se va cerrar la pantalla");
				driver.close();
				}
				catch( Exception r ){
					try {
					WebElement nombre=driver.findElement(By.tagName("text"));
					System.out.println(nombre.isDisplayed());
					System.out.println(nombre.getText());
					if(nombre.getText().equals(rs)) {
						System.out.println("LOG::::::::::::::::: EL NOMBRE DE LA EMPRESA ES CORRECTO");
						driver.findElement(By.id("vUSUID")).sendKeys(user);
						Thread.sleep(2000);
						System.out.println("LOG::::::::::: SE INGRESO EL USUARIO");
						driver.findElement(By.id("vUSUPSW")).sendKeys(pass);
						Thread.sleep(2000);
						System.out.println("LOG::::::::::: SE INGRESO EL PASSWORD");
						Thread.sleep(2000);
						driver.findElement(By.id("vIMAGEN8")).click();
						Thread.sleep(2000);
						alerta=driver.switchTo().alert().getText();
						System.out.println("Algo ha salido mal");
						System.out.println(alerta);
						/*
						Escribir=new PrintWriter (Archivo,"utf-8");
						Escribir.println(alerta);
						Escribir.close();
						*/
						driver.switchTo().alert().accept();
						System.out.println("Se va cerrar la pantalla");
						driver.close();
						}
					}
						catch( Exception e ){
							newURL=driver.getCurrentUrl();
							if(newURL!=url) {
							System.out.println("LOG::::::::::::::::::::::: ACCEDIMOS AL SISTEMA");
							System.out.println("LOG::::::::::: CERRAR SESIÓN");
							jse.executeScript("scroll(100,0);");
							Thread.sleep(2000);
							driver.findElement(By.id("MPW0005SALIR")).click();
							Thread.sleep(2000);
							/*
							Archivo =new File (resultado+timestamp+".doc");
							Escribir=new PrintWriter (Archivo,"utf-8");
							Escribir.println(exito);
							Escribir.close();
							*/
							}
							else {
								System.out.println("No se accedio al sistema");
							}
					}
				}
		}
			public static void correo(String url, String exito) throws InterruptedException{
				String correo="mmateo@interware.com.mx";
				String pass="Leviathan18#";
				url="https://www.google.com/intl/es-419/gmail/about/#";
				driver.get(url);
				Thread.sleep(2000);
				driver.findElement(By.linkText("Acceder")).click();
				ArrayList<String> tabs = new ArrayList <String>(driver.getWindowHandles());
				driver.switchTo().window(tabs.get(1));
				WebElement email=driver.findElement(By.tagName("input"));
				email.sendKeys(correo);
				email.sendKeys(Keys.ENTER);
				Thread.sleep(2000);
				WebElement password=driver.findElement(By.name("password"));
				password.sendKeys(pass);
				password.sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				driver.switchTo().window(tabs.get(0));
				driver.close();
				driver.switchTo().window(tabs.get(1));
				driver.findElement(By.xpath("//div[@gh=\"cm\"]")).click();
				Thread.sleep(3000);
				driver.findElement(By.id(":pk")).sendKeys(correo);
				Thread.sleep(1000);
				driver.findElement(By.id(":p2")).sendKeys("Resultado de la prueba");
				Thread.sleep(1000);
				driver.findElement(By.id(":q5")).sendKeys(Keys.ENTER);
				driver.findElement(By.id(":q5")).sendKeys(Keys.ENTER);
				Thread.sleep(1000);
				driver.findElement(By.id(":q5")).sendKeys(exito);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//div[@data-tooltip-delay=\"800\"]")).click();
				Thread.sleep(3000);
			}
}
			