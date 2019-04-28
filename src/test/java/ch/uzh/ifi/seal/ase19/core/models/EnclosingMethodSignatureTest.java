package ch.uzh.ifi.seal.ase19.core.models;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.codeelements.IParameterName;
import cc.kave.commons.model.naming.types.ITypeName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnclosingMethodSignatureTest {

    @Test
    void isSuperType() {
        String fullyQualifiedName = "com.myClass";
        ITypeName tn = mock(ITypeName.class);
        when(tn.getFullName()).thenReturn(fullyQualifiedName);

        IMethodName m = mock(IMethodName.class);
        when(m.getDeclaringType()).thenReturn(tn);
        when(m.getFullName()).thenReturn(fullyQualifiedName);
        when(m.getReturnType()).thenReturn(tn);
        List<IParameterName> params = new ArrayList<IParameterName>(){{
            IParameterName pm = mock(IParameterName.class);
            when(pm.getName()).thenReturn("name");
            ITypeName tn = mock(ITypeName.class);
            when(tn.getFullName()).thenReturn("com.Type");
            when(pm.getValueType()).thenReturn(tn);
            add(pm);
        }};
        when(m.getParameters()).thenReturn(params);

        EnclosingMethodSignature ems1 = new EnclosingMethodSignature(m);
        EnclosingMethodSignature ems2 = new EnclosingMethodSignature(m);

        Assertions.assertTrue(ems1.isSuperType(ems2));
    }

    @Test
    void isNotSuperTypeOtherType() {
        String fullyQualifiedName1 = "com.myClass";
        ITypeName tn1 = mock(ITypeName.class);
        when(tn1.getFullName()).thenReturn(fullyQualifiedName1);

        IMethodName mn1 = mock(IMethodName.class);
        when(mn1.getDeclaringType()).thenReturn(tn1);
        when(mn1.getFullName()).thenReturn(fullyQualifiedName1);
        when(mn1.getReturnType()).thenReturn(tn1);
        List<IParameterName> params1 = new ArrayList<IParameterName>(){{
            IParameterName pm = mock(IParameterName.class);
            when(pm.getName()).thenReturn("name");
            ITypeName tn = mock(ITypeName.class);
            when(tn.getFullName()).thenReturn("com.Type");
            when(pm.getValueType()).thenReturn(tn);
            add(pm);
        }};
        when(mn1.getParameters()).thenReturn(params1);

        EnclosingMethodSignature ems1 = new EnclosingMethodSignature(mn1);

        String fullyQualifiedName2 = "com.myOtherClass";
        ITypeName tn2 = mock(ITypeName.class);
        when(tn2.getFullName()).thenReturn(fullyQualifiedName2);

        IMethodName mn2 = mock(IMethodName.class);
        when(mn2.getDeclaringType()).thenReturn(tn2);
        when(mn2.getFullName()).thenReturn(fullyQualifiedName2);
        when(mn2.getReturnType()).thenReturn(tn2);
        List<IParameterName> params2 = new ArrayList<IParameterName>(){{
            IParameterName pm = mock(IParameterName.class);
            when(pm.getName()).thenReturn("name");
            ITypeName tn = mock(ITypeName.class);
            when(tn.getFullName()).thenReturn("com.OtherType");
            when(pm.getValueType()).thenReturn(tn);
            add(pm);
        }};
        when(mn2.getParameters()).thenReturn(params2);

        EnclosingMethodSignature ems2 = new EnclosingMethodSignature(mn2);

        Assertions.assertFalse(ems1.isSuperType(ems2));
    }

    @Test
    void isNotSuperTypeOtherName() {
        String fullyQualifiedName1 = "com.myClass";
        ITypeName tn1 = mock(ITypeName.class);
        when(tn1.getFullName()).thenReturn(fullyQualifiedName1);

        IMethodName mn1 = mock(IMethodName.class);
        when(mn1.getDeclaringType()).thenReturn(tn1);
        when(mn1.getFullName()).thenReturn(fullyQualifiedName1);
        when(mn1.getReturnType()).thenReturn(tn1);
        List<IParameterName> params1 = new ArrayList<IParameterName>(){{
            IParameterName pm = mock(IParameterName.class);
            when(pm.getName()).thenReturn("name");
            ITypeName tn = mock(ITypeName.class);
            when(tn.getFullName()).thenReturn("com.Type");
            when(pm.getValueType()).thenReturn(tn);
            add(pm);
        }};
        when(mn1.getParameters()).thenReturn(params1);

        EnclosingMethodSignature ems1 = new EnclosingMethodSignature(mn1);

        String fullyQualifiedName2 = "com.myOtherClass";
        ITypeName tn2 = mock(ITypeName.class);
        when(tn2.getFullName()).thenReturn(fullyQualifiedName2);

        IMethodName mn2 = mock(IMethodName.class);
        when(mn2.getDeclaringType()).thenReturn(tn2);
        when(mn2.getFullName()).thenReturn(fullyQualifiedName2);
        when(mn2.getReturnType()).thenReturn(tn2);
        List<IParameterName> params2 = new ArrayList<IParameterName>(){{
            IParameterName pm = mock(IParameterName.class);
            when(pm.getName()).thenReturn("otherName");
            ITypeName tn = mock(ITypeName.class);
            when(tn.getFullName()).thenReturn("com.Type");
            when(pm.getValueType()).thenReturn(tn);
            add(pm);
        }};
        when(mn2.getParameters()).thenReturn(params2);

        EnclosingMethodSignature ems2 = new EnclosingMethodSignature(mn2);

        Assertions.assertFalse(ems1.isSuperType(ems2));
    }

    @Test
    void testEquals() {
        String fullyQualifiedName = "com.myClass";
        ITypeName tn = mock(ITypeName.class);
        when(tn.getFullName()).thenReturn(fullyQualifiedName);

        IMethodName m = mock(IMethodName.class);
        when(m.getDeclaringType()).thenReturn(tn);
        when(m.getFullName()).thenReturn(fullyQualifiedName);
        when(m.getReturnType()).thenReturn(tn);
        when(m.getParameters()).thenReturn(new ArrayList<>());

        EnclosingMethodSignature ems1 = new EnclosingMethodSignature(m);
        EnclosingMethodSignature ems2 = new EnclosingMethodSignature(m);

        Assertions.assertEquals(ems1, ems2);
    }

    @Test
    void notEquals() {
        String fullyQualifiedName1 = "com.myClass";
        ITypeName tn1 = mock(ITypeName.class);
        when(tn1.getFullName()).thenReturn(fullyQualifiedName1);

        IMethodName mn1 = mock(IMethodName.class);
        when(mn1.getDeclaringType()).thenReturn(tn1);
        when(mn1.getFullName()).thenReturn(fullyQualifiedName1);
        when(mn1.getReturnType()).thenReturn(tn1);
        when(mn1.getParameters()).thenReturn(new ArrayList<>());

        EnclosingMethodSignature ems1 = new EnclosingMethodSignature(mn1);

        String fullyQualifiedName2 = "com.myOtherClass";
        ITypeName tn2 = mock(ITypeName.class);
        when(tn2.getFullName()).thenReturn(fullyQualifiedName2);

        IMethodName mn2 = mock(IMethodName.class);
        when(mn2.getDeclaringType()).thenReturn(tn2);
        when(mn2.getFullName()).thenReturn(fullyQualifiedName2);
        when(mn2.getReturnType()).thenReturn(tn2);
        when(mn2.getParameters()).thenReturn(new ArrayList<>());

        EnclosingMethodSignature ems2 = new EnclosingMethodSignature(mn2);

        Assertions.assertNotEquals(fullyQualifiedName1, fullyQualifiedName2);
        Assertions.assertNotEquals(ems1, ems2);
    }

    @Test
    void testHashCode() {
        String fullyQualifiedName = "com.myClass";
        ITypeName tn = mock(ITypeName.class);
        when(tn.getFullName()).thenReturn(fullyQualifiedName);

        IMethodName m = mock(IMethodName.class);
        when(m.getDeclaringType()).thenReturn(tn);
        when(m.getFullName()).thenReturn(fullyQualifiedName);
        when(m.getReturnType()).thenReturn(tn);
        when(m.getParameters()).thenReturn(new ArrayList<>());

        EnclosingMethodSignature ems1 = new EnclosingMethodSignature(m);
        EnclosingMethodSignature ems2 = new EnclosingMethodSignature(m);

        Assertions.assertEquals(ems1.hashCode(), ems2.hashCode());
    }

    @Test
    void notSameHashCode() {
        String fullyQualifiedName1 = "com.myClass";
        ITypeName tn1 = mock(ITypeName.class);
        when(tn1.getFullName()).thenReturn(fullyQualifiedName1);

        IMethodName mn1 = mock(IMethodName.class);
        when(mn1.getDeclaringType()).thenReturn(tn1);
        when(mn1.getFullName()).thenReturn(fullyQualifiedName1);
        when(mn1.getReturnType()).thenReturn(tn1);
        when(mn1.getParameters()).thenReturn(new ArrayList<>());

        EnclosingMethodSignature ems1 = new EnclosingMethodSignature(mn1);

        String fullyQualifiedName2 = "com.myOtherClass";
        ITypeName tn2 = mock(ITypeName.class);
        when(tn2.getFullName()).thenReturn(fullyQualifiedName2);

        IMethodName mn2 = mock(IMethodName.class);
        when(mn2.getDeclaringType()).thenReturn(tn2);
        when(mn2.getFullName()).thenReturn(fullyQualifiedName2);
        when(mn2.getReturnType()).thenReturn(tn2);
        when(mn2.getParameters()).thenReturn(new ArrayList<>());

        EnclosingMethodSignature ems2 = new EnclosingMethodSignature(mn2);

        Assertions.assertNotEquals(fullyQualifiedName1, fullyQualifiedName2);
        Assertions.assertNotEquals(ems1.hashCode(), ems2.hashCode());
    }
}