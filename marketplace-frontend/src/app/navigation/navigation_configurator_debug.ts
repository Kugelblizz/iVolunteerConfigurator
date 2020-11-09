// http://localhost:4200/main/task-select?tenantId=5f92c841eada0c0d9dfa877a

// http://localhost:4200/main/class-configurator?tenantId=5f92c841eada0c0d9dfa877a
// http://localhost:4200/main/properties/all?tenantId=5f92c841eada0c0d9dfa877a
// http://localhost:4200/main/matching-configurator?tenantId=5f92c841eada0c0d9dfa877a




const tenantId = '5f92c841eada0c0d9dfa877a';
const tenantIdParam = '/tenantId/';

export const navigation_configurator_debug = [
  {
    id: 'welcome',
    title: 'Übersicht',
    type: 'item',
    icon: 'door-open',
    url: '/main/dashboard' + tenantIdParam + tenantId
  },
  {
    id: 'digi',
    title: 'Digitalisieren',
    type: 'group'
  },
  {
    id: 'tasks',
    title: 'Einträge erfassen',
    type: 'item',
    icon: 'pencil',
    url: '/main/task-select' + tenantIdParam + tenantId
  },
  {
    id: 'config',
    title: 'Konfigurieren',
    type: 'group'
  },
  {
    id: 'configurator',
    title: 'Einträge konfigurieren',
    type: 'item',
    icon: 'cogs',
    url: '/main/class-configurator' + tenantIdParam + tenantId
  },
  {
    id: 'matching-configurator',
    title: 'Matching konfigurieren',
    type: 'item',
    icon: 'less-than-equal-solid',
    url: '/main/matching-configurator' + tenantIdParam + tenantId
  },
  {
    id: 'rule-configurator',
    title: 'Einträge ableiten',
    type: 'item',
    icon: 'share-square',
    url: '/main/rules/all' + tenantIdParam + tenantId
  },
  {
    id: 'property-builder',
    title: 'Properties konfigurieren',
    type: 'item',
    icon: 'hammer-solid',
    url: '/main/properties/all' + tenantIdParam + tenantId
  }
];
