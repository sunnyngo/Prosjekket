# Code file created by Pascal2016 compiler 2016-12-14 22:35:11
        .globl  main                    
main:
        call    prog$MINI_1             # Start program
        movl    $0,%eax                 # Set status 0 and
        ret                             # terminate the program
prog$MINI_1:
        enter   $32,$1                  # Start of MINI_1
        movl    $120,%eax               #   'x'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        leave                           # End of MINI_1
        ret                             
