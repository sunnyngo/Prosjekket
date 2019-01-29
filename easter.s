# Code file created by Pascal2016 compiler 2016-12-16 15:12:43
        .globl  main                    
main:
        call    prog$EASTER_1           # Start program
        movl    $0,%eax                 # Set status 0 and
        ret                             # terminate the program
proc$EASTER_2:
        enter   $92,$2                  # Start of EASTER_2
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  Y
        pushl   %eax                    
        movl    $19,%eax                # 19
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               # mod
        movl    -8(%ebp),%edx           
        movl    %eax,-36(%edx)          # A:=
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  Y
        pushl   %eax                    
        movl    $100,%eax               # 100
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-40(%edx)          # B:=
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  Y
        pushl   %eax                    
        movl    $100,%eax               # 100
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               # mod
        movl    -8(%ebp),%edx           
        movl    %eax,-44(%edx)          # C:=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  B
        pushl   %eax                    
        movl    $4,%eax                 # 4
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-48(%edx)          # D:=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  B
        pushl   %eax                    
        movl    $4,%eax                 # 4
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               # mod
        movl    -8(%ebp),%edx           
        movl    %eax,-52(%edx)          # E:=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  B
        pushl   %eax                    
        movl    $8,%eax                 # 8
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        pushl   %eax                    
        movl    $25,%eax                # 25
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-56(%edx)          # F:=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  B
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -56(%edx),%eax          #  F
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # -
        movl    -8(%ebp),%edx           
        movl    -56(%edx),%eax          #  F
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        pushl   %eax                    
        movl    $3,%eax                 # 3
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-60(%edx)          # G:=
        movl    $19,%eax                # 19
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #  A
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  B
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  B
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -48(%edx),%eax          #  D
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # -
        movl    -8(%ebp),%edx           
        movl    -48(%edx),%eax          #  D
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -60(%edx),%eax          #  G
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # -
        movl    -8(%ebp),%edx           
        movl    -60(%edx),%eax          #  G
        pushl   %eax                    
        movl    $15,%eax                # 15
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        pushl   %eax                    
        movl    $30,%eax                # 30
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               # mod
        movl    -8(%ebp),%edx           
        movl    %eax,-64(%edx)          # H:=
        movl    -8(%ebp),%edx           
        movl    -44(%edx),%eax          #  C
        pushl   %eax                    
        movl    $4,%eax                 # 4
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-68(%edx)          # I:=
        movl    -8(%ebp),%edx           
        movl    -44(%edx),%eax          #  C
        pushl   %eax                    
        movl    $4,%eax                 # 4
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               # mod
        movl    -8(%ebp),%edx           
        movl    %eax,-72(%edx)          # K:=
        movl    $32,%eax                # 32
        pushl   %eax                    
        movl    $2,%eax                 # 2
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -52(%edx),%eax          #  E
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        movl    $2,%eax                 # 2
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -52(%edx),%eax          #  E
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        pushl   %eax                    
        movl    $2,%eax                 # 2
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -68(%edx),%eax          #  I
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        movl    $2,%eax                 # 2
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -68(%edx),%eax          #  I
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  H
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # -
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  H
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -72(%edx),%eax          #  K
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # -
        pushl   %eax                    
        movl    $7,%eax                 # 7
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               # mod
        movl    -8(%ebp),%edx           
        movl    %eax,-76(%edx)          # L:=
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #  A
        pushl   %eax                    
        movl    $11,%eax                # 11
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  H
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        movl    $11,%eax                # 11
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  H
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        pushl   %eax                    
        movl    $22,%eax                # 22
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -76(%edx),%eax          #  L
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        pushl   %eax                    
        movl    $451,%eax               # 451
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-80(%edx)          # M:=
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  H
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -76(%edx),%eax          #  L
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        movl    -8(%ebp),%edx           
        movl    -76(%edx),%eax          #  L
        pushl   %eax                    
        movl    $7,%eax                 # 7
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -80(%edx),%eax          #  M
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # -
        movl    $7,%eax                 # 7
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -80(%edx),%eax          #  M
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        pushl   %eax                    
        movl    $114,%eax               # 114
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        pushl   %eax                    
        movl    $31,%eax                # 31
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-84(%edx)          # MONTH:=
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  H
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -76(%edx),%eax          #  L
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        movl    -8(%ebp),%edx           
        movl    -76(%edx),%eax          #  L
        pushl   %eax                    
        movl    $7,%eax                 # 7
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -80(%edx),%eax          #  M
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # -
        movl    $7,%eax                 # 7
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -80(%edx),%eax          #  M
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # *
        pushl   %eax                    
        movl    $114,%eax               # 114
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        pushl   %eax                    
        movl    $31,%eax                # 31
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               # mod
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        movl    -8(%ebp),%edx           
        movl    %eax,-88(%edx)          # DAY:=
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    -84(%edx),%eax          #  MONTH
        pushl   %eax                    
        movl    $3,%eax                 # 3
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test =
        cmpl    $0,%eax                 
        je      .L0003                  
        movl    -8(%ebp),%edx           
        movl    -88(%edx),%eax          #  DAY
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $77,%eax                #   'M'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $97,%eax                #   'a'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $114,%eax               #   'r'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $99,%eax                #   'c'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $104,%eax               #   'h'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  Y
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #  10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        jmp     .L0004                  
.L0003:
        movl    -8(%ebp),%edx           
        movl    -88(%edx),%eax          #  DAY
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $65,%eax                #   'A'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $112,%eax               #   'p'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $114,%eax               #   'r'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $105,%eax               #   'i'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $108,%eax               #   'l'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  Y
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #  10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
.L0004:
                                        # End if-statement
        leave                           # End of EASTER_2
        ret                             
prog$EASTER_1:
        enter   $36,$1                  # Start of EASTER_1
        movl    $2010,%eax              # 2010
        movl    -4(%ebp),%edx           
        movl    %eax,-36(%edx)          # Y:=
.L0005:
                                        # Start while-statement
        movl    -4(%ebp),%edx           
        movl    -36(%edx),%eax          #  Y
        pushl   %eax                    
        movl    $2020,%eax              # 2020
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     
        cmpl    $0,%eax                 
        je      .L0006                  
        movl    -4(%ebp),%edx           
        movl    -36(%edx),%eax          #  Y
        pushl   %eax                    # Push param #1.
        call    proc$EASTER_2           
        addl    $4,%esp                 # Pop parameters.
        movl    -4(%ebp),%edx           
        movl    -36(%edx),%eax          #  Y
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # +
        movl    -4(%ebp),%edx           
        movl    %eax,-36(%edx)          # Y:=
        jmp     .L0005                  
.L0006:
                                        # End while-statement
        leave                           # End of EASTER_1
        ret                             
