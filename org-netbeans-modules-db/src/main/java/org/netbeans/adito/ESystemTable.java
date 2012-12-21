package org.netbeans.adito;

/**
 * Repräsentiert die Namen der Systemtabellen.
 * @author Thomas Tasior 12.12.12, 10:41
 */
public enum ESystemTable
{
  ASYS_SYSTEM ,
  ASYS_SEQUENCES ,
  ASYS_BINARIES ,
  ASYS_ICONS ,
  ASYS_MAILREPOSIT ,
  ASYS_MAILREPOSIT_HASH ,
  ASYS_CALENDAR ,
  ASYS_CALENDARLINK ,
  ASYS_CALENDAR_AU ,
  ASYS_CALENDARCACHE ,
  ASYS_CALENDARSYNC ,
  ASYS_UIDRESOLVER ,
  ASYS_TIMER ,
  ASYS_FARM ,
  ASYS_FARM_CLIENT ,
  ASYS_AUDIT ,
  ASYS_SYNCSLAVES ;

  //Nur für DigestException Entwicklung
  //public static void main(String[] args)
  //{
  //  for (int i = 0; i < values().length; i++)
  //  {
  //    System.out.println("drop table "+values()[i]+";");
  //
  //  }
  //}

}
