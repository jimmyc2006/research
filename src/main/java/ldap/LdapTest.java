package ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapTest {
  public void JNDILookup() {
    String root = "dc=my-domain,dc=com";
    Hashtable<String, String> env = new Hashtable<>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://127.0.0.1/" + root );
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, "cn=Manager,dc=my-domain,dc=com");
    env.put(Context.SECURITY_CREDENTIALS, "qwe@123");
    DirContext ctx = null;

    try {
      ctx = new InitialDirContext(env);
      Attributes attrs = ctx.getAttributes("cn=wei.xu,ou=jishu");
      NamingEnumeration ne = attrs.getAll();
      while(ne.hasMore()) {
        System.out.println(ne.next());
      }
    } catch (javax.naming.AuthenticationException e) {
      e.printStackTrace();
      System.out.println("认证失败");
    } catch (Exception e) {
      System.out.println("认证出错：");
      e.printStackTrace();
    }
    if (ctx != null) {
      try {
        ctx.close();
      } catch (NamingException e) {
        // ignore
      }
    }
  }

  public static void main(String[] args) {
    LdapTest LDAPTest = new LdapTest();
    LDAPTest.JNDILookup();
  }
}
