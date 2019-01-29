# Code file created by Pascal2016 compiler 2016-12-16 15:12:15
        .globl  main                    
main:
        call    prog$GCD_1              # Start program
        movl    $0,%eax                 # Set status 0 and
        ret                             # terminate the program
func$GCD_2:
        enter   $32,$2                  # Start of GCD_2
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    12(%edx),%eax           #  N
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test =
        cmpl    $0,%eax                 
        je      .L0003                  
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  M
        movl    -8(%ebp),%edx           
        movl    %eax,-32(%edx)          # GCD:=
        jmp     .L0004                  
.L0003:
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  M
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    12(%edx),%eax           #  N
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               # mod
        pushl   %eax                    # Push param #2.
        movl    -8(%ebp),%edx           
        movl    12(%edx),%eax           #  N
        pushl   %eax                    # Push param #1.
        call    func$GCD_2              
        addl    $8,%esp                 # Pop parameters
        movl    -8(%ebp),%edx           
        movl    %eax,-32(%edx)          # GCD:=
.L0004:
                                        # End if-statement
        movl    -32(%ebp),%eax          
        leave                           # End of GCD_2
        ret                             
prog$GCD_1:
        enter   $36,$1                  # Start of GCD_1
        movl    $462,%eax               # 462
        pushl   %eax                    # Push param #2.
        movl    $1071,%eax              # 1071
        pushl   %eax                    # Push param #1.
        call    func$GCD_2              
        addl    $8,%esp                 # Pop parameters
        movl    -4(%ebp),%edx           
        movl    %eax,-36(%edx)          # RES:=
        movl    $71,%eax                #   'G'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $67,%eax                #   'C'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $68,%eax                #   'D'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $40,%eax                #   '('
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $1071,%eax              # 1071
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $44,%eax                #   ','
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $462,%eax               # 462
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $41,%eax                #   ')'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -4(%ebp),%edx           
        movl    -36(%edx),%eax          #  RES
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #  10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        leave                           # End of GCD_1
        ret                             
