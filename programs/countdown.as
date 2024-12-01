CAL .main
HLT
.main
LDI r1 251
STR r1 r0 0
LDI r10 10
.main_whileStart2
LDI r4 -1
SUB r10 r4 r0
BRH EQ .main_whileEnd3
ADD r10 r0 r1
LDI r2 250
STR r2 r1 0
ADI r10 -1
JMP .main_whileStart2
.main_whileEnd3
RET
