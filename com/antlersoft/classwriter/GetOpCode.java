
/**
 * Title:        <p>
 * Description:  Java object database; also code analysis tool<p>
 * <p>Copyright (c) 2000-2005  Michael A. MacDonald<p>
 * ----- - - -- - - --
 * <p>
 *     This package is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 * <p>
 *     This package is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * <p>
 *     You should have received a copy of the GNU General Public License
 *     along with the package (see gpl.txt); if not, see www.gnu.org
 * <p>
 * ----- - - -- - - --
 * Company:      <p>
 * @author Michael MacDonald
 * @version 1.0
 */
package com.antlersoft.classwriter;

import java.util.Stack;

public class GetOpCode extends SimpleOpCode
{
	GetOpCode( int v, int l, String m)
    {
        super( v, l, m, null);
    }

    Stack stackUpdate( Instruction instruction, Stack old_stack,
    	CodeAttribute attribute)
    	throws CodeCheckException
    {
        Object stack_type=TypeParse.stackCategory( TypeParse.parseFieldType(
        	instruction.getSymbolicReference( attribute.getCurrentClass()).
         	getSymbolType()));
        if ( stack_type==null)
        	throw new CodeCheckException( "GetOpCode-- bad operand type");
        Stack new_stack=(Stack)old_stack.clone();

        if ( getMnemonic().startsWith( "put"))
        {
            if ( new_stack.pop()!=stack_type)
            	throw new CodeCheckException( "put value wrong size");
        }

		if ( ! getMnemonic().substring( 3).equals( "static"))
  		{
         	if ( new_stack.size()<1)
         	{
            	throw new CodeCheckException( "getfield: stack not big enough");
          	}
            if ( new_stack.pop()!=ProcessStack.CAT1)
            	throw new CodeCheckException( "getfield: CAT2 on stack");
        }

 		if ( getMnemonic().startsWith( "get"))
   		{
			new_stack.push( stack_type);
     	}
        return new_stack;
    }
}
