package org.netbeans.modules.form.adito.actions;


import javax.swing.*;
import java.util.*;

/**
 * Liste die AditoActionObjecte nach ihrer Position automatisch sortiert
 * Es werden keine Actions eingefügt, die sich schon in der Liste befinden
 *
 * @author T. Feldmann, 07.03.13
 */
public class AditoSortedActionList
{

  private List<AditoActionObject> list;
  private Comparator<AditoActionObject> aditoActionObjectComparator;

  public AditoSortedActionList()
  {
    list = new ArrayList<AditoActionObject>();
    aditoActionObjectComparator = new Comparator<AditoActionObject>()
    {
      @Override
      public int compare(AditoActionObject o1, AditoActionObject o2)
      {
        return o1.getPositon() - o2.getPositon();
      }
    };

  }

  private void _sortActionList()
  {
    Collections.sort(list, aditoActionObjectComparator);
  }

  public int size()
  {
    return list.size();
  }


  public boolean isEmpty()
  {
    return list.isEmpty();
  }


  public boolean contains(AditoActionObject o)
  {
    return list.contains(o);
  }


  public int indexOf(AditoActionObject o)
  {
    return list.indexOf(o);
  }


  public int lastIndexOf(AditoActionObject o)
  {
    return list.lastIndexOf(o);
  }


  public Action[] toActionArray()
  {
    List<Action> actions = new ArrayList<Action>();
    _sortActionList();
    for (AditoActionObject obj : list)
      actions.add(obj.getAction());

    return actions.toArray(new Action[actions.size()]);
  }

  public Object[] toArray()
  {
    _sortActionList();
    return list.toArray();
  }


  public AditoActionObject get(int index)
  {
    _sortActionList();
    return list.get(index);
  }

  public boolean add(AditoActionObject e)
  {
    for (AditoActionObject obj : list)
      if (obj.getAction() != null && e.getAction() != null && obj.getAction().equals(e.getAction()))
        return false;

    boolean status = list.add(e);
    _sortActionList();
    return status;
  }


  public AditoActionObject remove(int index)
  {
    AditoActionObject object = list.remove(index);
    _sortActionList();
    return object;
  }


  public boolean remove(AditoActionObject o)
  {
    boolean status = list.remove(o);
    _sortActionList();
    return status;
  }


  public void clear()
  {
    list.clear();
  }


  public boolean addAll(Collection c)
  {
    boolean status = list.addAll(c);
    _sortActionList();
    return status;
  }


  public boolean removeAll(Collection<?> c)
  {
    boolean status = list.removeAll(c);
    _sortActionList();
    return status;
  }


  public boolean retainAll(Collection<?> c)
  {
    boolean status = list.retainAll(c);
    _sortActionList();
    return status;
  }


  public ListIterator listIterator(int index)
  {
    return list.listIterator(index);
  }


  public ListIterator listIterator()
  {
    return list.listIterator();
  }


  public Iterator iterator()
  {
    return list.iterator();
  }


  public List subList(int fromIndex, int toIndex)
  {
    _sortActionList();
    return list.subList(fromIndex, toIndex);
  }
}
