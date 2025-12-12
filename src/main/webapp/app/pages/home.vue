<template>
  <div
    v-if="!authenticated"
    class="flex flex-row justify-center"
  >
    <div class="flex flex-row gap-x-1 w-1/4">
      <!-- <Button
        :text="$t('register.form.button')"
        :size="'md'"
        @click="$router.push('register')"
      /> -->

      <Button
        :text="$t('global.messages.info.authenticated.link')"
        :size="'md'"
        @click="openLogin()"
      />
    </div>
  </div>
  <div 
    v-else
  >
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <div class="flex items-center justify-between mt-4 md:mt-8 mb-4 md:mb-6">
      <div class="text-xl md:text-2xl font-semibold tracking-tight">{{$t('home.title')}}</div>
      <div>
        <Button :text="editMode ? $t('home.common.save') : $t('home.common.customize')" :size="'sm'" @click="toggleEditMode" />
      </div>
    </div>
    <div v-if="featureOverview" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 md:gap-6 auto-rows-fr mt-4 mb-10">
      <div
        v-for="(w, index) in widgetOrder.filter(k => visibleMap[k])"
        :key="w"
        class="relative"
        :draggable="editMode"
        @dragstart="onDragStart(index)"
        @dragover.prevent
        @drop="onDrop(index)"
      >
        <div v-if="editMode" class="absolute top-2 right-3 text-xs text-gray-400 select-none">ziehen</div>

        <div v-if="w === 'kpi'" class="h-full"><KpiWidget :items="kpiItems" @more="goToAnalytics" @refresh="refreshKpis" /></div>
        <div v-else-if="w === 'tasks'" class="h-full"><TasksWidget :items="taskItems" @more="goToTasks" @add="addTask" /></div>
        <div v-else-if="w === 'docs'" class="h-full"><DocumentsWidget :items="docItems" @more="goToDocuments" @upload="uploadDocument" /></div>
        <div v-else-if="w === 'birthdays'" class="h-full"><BirthdaysWidget :items="birthdayItems" @more="goToContacts" @addReminder="addBirthdayReminder" /></div>
        <div v-else-if="w === 'calendar'" class="h-full"><CalendarWidget :items="calendarItems" @more="goToCalendar" @addEvent="addEvent" /></div>
        <div v-else-if="w === 'terminations'" class="h-full"><TerminationsWidget :items="terminationItems" @more="goToContracts" /></div>
        <div v-else-if="w === 'changes'" class="h-full"><ChangesWidget :items="changeItems" @more="goToChanges" /></div>
        <div v-else-if="w === 'loans'" class="h-full"><LoansWidget :items="loanItems" :borrowed="borrowedItems" @more="goToLoans" /></div>
        <div v-else-if="w === 'finance'" class="h-full"><FinanceOverviewWidget :items="financeItems" @more="goToFinance" /></div>
        <div v-else-if="w === 'incomeExpense'" class="h-full"><IncomeExpenseWidget :items="incomeExpenseItems" @more="goToFinance" /></div>
        <div v-else-if="w === 'favorites'" class="h-full"><FavoritesWidget :items="favoriteItems" @open="openFavorite" @remove="removeFavorite" @rename="renameFavorite" /></div>
        <div v-else-if="w === 'budgets'" class="h-full"><BudgetsWidget :items="budgetItems" /></div>
        <div v-else-if="w === 'cashflow'" class="h-full"><CashflowWidget :values="cashflowValues" /></div>
        <div v-else-if="w === 'searchShortcuts'" class="h-full"><SearchShortcutsWidget :items="searchShortcutItems" @open="openShortcut" /></div>
        <div v-else-if="w === 'generalChores'" class="h-full"><GeneralChoresWidget :items="generalChoreItems" /></div>
        <div v-else-if="w === 'mobility'" class="h-full"><MobilityWidget :items="mobilityItems" /></div>
        <div v-else-if="w === 'warranties'" class="h-full"><WarrantiesWidget :items="warrantyItems" /></div>
      </div>
    </div>
    <ManageWidgetsDialog
      v-if="editMode && manageOpen"
      :all-widgets="allWidgets"
      :visible-map="visibleMap"
      @update:visibleMap="updateVisibleMap"
      @resetOrder="resetOrder"
      @close="manageOpen=false"
    />
    </div>
  </div>
</template>

<script setup>
import { computed, onBeforeMount, onMounted, ref } from 'vue';
import LoginService from '@/services/login.service';
import AccountService from '@/services/account.service';
import Button from '@/components/buttons/button.vue';
import { useStore } from 'vuex';
import { Authority } from '@/shared/security/authority';
import { useRouter } from 'vue-router';
import KpiWidget from '@/components/widgets/KpiWidget.vue';
import TasksWidget from '@/components/widgets/TasksWidget.vue';
import DocumentsWidget from '@/components/widgets/DocumentsWidget.vue';
import BirthdaysWidget from '@/components/widgets/BirthdaysWidget.vue';
import CalendarWidget from '@/components/widgets/CalendarWidget.vue';
import TerminationsWidget from '@/components/widgets/TerminationsWidget.vue';
import ChangesWidget from '@/components/widgets/ChangesWidget.vue';
import LoansWidget from '@/components/widgets/LoansWidget.vue';
import FinanceOverviewWidget from '@/components/widgets/FinanceOverviewWidget.vue';
import IncomeExpenseWidget from '@/components/widgets/IncomeExpenseWidget.vue';
import ManageWidgetsDialog from '@/components/ui/ManageWidgetsDialog.vue';
import FavoritesWidget from '@/components/widgets/FavoritesWidget.vue';
import BudgetsWidget from '@/components/widgets/BudgetsWidget.vue';
import CashflowWidget from '@/components/widgets/CashflowWidget.vue';
import { getFeatureFlag } from '@/shared/config/featureFlags';
import SearchShortcutsWidget from '@/components/widgets/SearchShortcutsWidget.vue';
import GeneralChoresWidget from '@/components/widgets/GeneralChoresWidget.vue';
import MobilityWidget from '@/components/widgets/MobilityWidget.vue';
import WarrantiesWidget from '@/components/widgets/WarrantiesWidget.vue';
import OverviewService from '@/services/overview.service';

const store = useStore();
const router = useRouter();

const tileRoutes = computed(() => router.getRoutes().filter(route => route.meta.type === 'tile'));

const authenticated = computed(() => store.getters['account/authenticated']);

const username = computed(() => store.getters['account/account']?.login ?? '');

const openLogin = (() => { 
  LoginService.openLogin();
});

// Ansicht anpassen (später Drag&Drop). Jetzt nur Toggle.
const editMode = ref(false);
const manageOpen = ref(false);
const toggleEditMode = () => { editMode.value = !editMode.value; manageOpen.value = editMode.value; };
const featureOverview = ref(getFeatureFlag('FEATURE_OVERVIEW', true));

// Platzhalterdaten für Widgets (MVP)
const widgetOrderKey = computed(() => `homeWidgetOrder:${username.value}`);
const favoritesKey = computed(() => `homeFavorites:${username.value}`);
const defaultWidgetOrder = ['kpi', 'tasks', 'docs', 'birthdays', 'calendar', 'terminations', 'changes', 'loans', 'finance', 'incomeExpense', 'favorites', 'budgets', 'cashflow', 'searchShortcuts', 'generalChores', 'mobility', 'warranties'];
const widgetOrder = ref([...defaultWidgetOrder]);
const allWidgets = [
  { key: 'kpi', label: 'KPIs' },
  { key: 'tasks', label: 'Aufgaben' },
  { key: 'docs', label: 'Eingangsdokumente' },
  { key: 'birthdays', label: 'Geburtstage' },
  { key: 'calendar', label: 'Kalender' },
  { key: 'terminations', label: 'Kündigungstermine' },
  { key: 'changes', label: 'Änderungen' },
  { key: 'loans', label: 'Verliehene Sachen' },
  { key: 'finance', label: 'Finanzübersicht' },
  { key: 'incomeExpense', label: 'Einnahmen / Ausgaben' },
  { key: 'favorites', label: 'Favoriten' },
  { key: 'budgets', label: 'Budgets' },
  { key: 'cashflow', label: 'Cashflow' },
  { key: 'searchShortcuts', label: 'Such-Shortcuts' },
  { key: 'generalChores', label: 'Allgemeine Erledigungen' },
  { key: 'mobility', label: 'Mobilität' },
  { key: 'warranties', label: 'Garantien/Service' },
];
const visibilityKey = computed(() => `homeWidgetVisibility:${username.value}`);
const role = computed(() => store.getters['account/account']?.authorities?.[0] ?? 'USER_BASIC');
const baseVisible = {
  kpi: true, tasks: true, docs: true, birthdays: true, calendar: true,
  terminations: false, changes: false, loans: false, finance: false, incomeExpense: false,
  favorites: true,
  budgets: role.value !== 'USER_LIGHT',
  cashflow: role.value !== 'USER_LIGHT',
  searchShortcuts: true,
  generalChores: true,
  mobility: true,
  warranties: true,
};
const visibleMap = ref(Object.fromEntries(defaultWidgetOrder.map(k => [k, baseVisible[k] ?? false])));

const kpiItems = ref([
  { label: 'Verträge', value: 124 },
  { label: 'Dokumente', value: 3421 },
  { label: 'Fällige Termine', value: 7 },
]);

const taskItems = ref([
  { title: 'Vertrag prüfen', due: 'Morgen', status: 'offen' },
  { title: 'Dokument zuordnen', due: 'Fr, 10.01.', status: 'offen' },
  { title: 'Kontakt aktualisieren', due: 'Nächste Woche', status: 'in Arbeit' },
]);

const docItems = ref([
  { title: 'Rechnung 2025-001.pdf', source: 'E-Mail', receivedAt: 'Heute' },
  { title: 'Versicherungsbrief.pdf', source: 'Post', receivedAt: 'Gestern' },
  { title: 'Kontoauszug.pdf', source: 'Upload', receivedAt: 'Mo' },
]);

const birthdayItems = ref([
  { name: 'Max Mustermann', date: '20.01.', age: 40, isRound: true },
  { name: 'Erika Muster', date: '28.01.', age: 31, isRound: false },
]);

const calendarItems = ref([
  { title: 'TÜV Geländewagen', date: '15.01. 10:00', location: 'DEKRA' },
  { title: 'Banktermin', date: '18.01. 14:00', location: 'Sparkasse' },
]);

const terminationItems = ref([
  { contract: 'Handyvertrag', notice: '3 Monate', date: '01.03.25' },
  { contract: 'Versicherung', notice: '1 Monat', date: '15.02.25' },
]);

const changeItems = ref([
  { object: 'Kontakt: Max', change: 'Adresse aktualisiert', time: 'Heute 09:21' },
  { object: 'Dokument: Vertrag.pdf', change: 'hochgeladen', time: 'Gestern 14:10' },
]);

const loanItems = ref([
  { item: 'Bohrmaschine', to: 'Peter', returnBy: '31.01.25' },
  { item: 'Buch', to: 'Lisa', returnBy: '05.02.25' },
]);

const borrowedItems = ref([
  { item: 'Leiter', from: 'Nachbar Tom', returnBy: '22.01.25' },
]);

const financeItems = ref([
  { category: 'Monatsausgaben', amount: '1.240 €', period: 'Jan 25' },
  { category: 'Monatseinnahmen', amount: '2.850 €', period: 'Jan 25' },
]);

const incomeExpenseItems = ref([
  { title: 'Miete', amount: '-850 €', date: '01.01.25' },
  { title: 'Gehalt', amount: '+2.500 €', date: '01.01.25' },
]);

const defaultFavorites = [
  { label: 'Dokumente', name: 'DocumentList' },
  { label: 'Kontakte', name: 'ContactList' },
];
const favoriteItems = ref([...defaultFavorites]);

const budgetItems = ref([
  { category: 'Lebenshaltung', budget: 600, actual: 520, percent: Math.round((520/600) * 100) },
  { category: 'Mobilität', budget: 300, actual: 340, percent: Math.round((340/300) * 100) },
]);

const cashflowValues = ref([10,20,15,30,18,40,22,35,28,32,26,38]);
const searchShortcutItems = ref([
  { label: 'Dokumente: ohne Zuordnung', queryKey: 'docs-unassigned' },
  { label: 'Verträge: bald fällig', queryKey: 'contracts-soon' },
]);

const generalChoreItems = ref([
  { title: 'Steuer-ID prüfen', date: 'Jan', category: 'Steuern', urgent: false },
  { title: 'Rundfunkbeitrag', date: 'Feb', category: 'Abgaben', urgent: true },
]);

const mobilityItems = ref([
  { vehicle: 'VW Golf', km: 84215, hu: '06/25', service: '04/25', insurance: 'Haftpflicht' },
  { vehicle: 'E‑Bike', km: 3120, hu: '–', service: '03/25', insurance: 'Teilkasko' },
]);

const warrantyItems = ref([
  { item: 'Waschmaschine', until: '11/2026', kind: 'Hersteller' },
  { item: 'Smartphone', until: '05/2025', kind: 'Händler' },
]);

// Navigation/Actions (Platzhalter; nur sichere Routen nutzen oder no-op)
const goToAnalytics = () => {};
const refreshKpis = () => {};
const goToTasks = () => {};
const addTask = () => {};
const goToDocuments = () => { router.push({ name: 'DocumentList' }); };
const uploadDocument = () => {};
const goToContacts = () => { router.push({ name: 'ContactList' }); };
const addBirthdayReminder = () => {};
const goToCalendar = () => {};
const addEvent = () => {};
const goToContracts = () => { router.push({ name: 'ContractList' }); };
const goToChanges = () => {};
const goToLoans = () => {};
const goToFinance = () => {};
const openShortcut = (shortcut) => {
  if (!shortcut || !shortcut.queryKey) return;
  // Placeholder: nur einfache Navigationsentscheidungen
  if (shortcut.queryKey === 'docs-unassigned') router.push({ name: 'DocumentList' });
  if (shortcut.queryKey === 'contracts-soon') router.push({ name: 'ContractList' });
};
const openFavorite = (fav) => { if (fav?.name) router.push({ name: fav.name }); };
const saveFavorites = () => { try { localStorage.setItem(favoritesKey.value, JSON.stringify(favoriteItems.value)); } catch {} };
const removeFavorite = (idx) => { if (idx >= 0) { favoriteItems.value.splice(idx, 1); saveFavorites(); } };
const renameFavorite = (idx) => {
  const label = prompt('Neuer Name für Favorit:', favoriteItems.value[idx]?.label || '');
  if (label !== null && label !== undefined) {
    favoriteItems.value[idx] = { ...favoriteItems.value[idx], label };
    saveFavorites();
  }
};

onBeforeMount(() => { 
  if (!authenticated.value) {
    AccountService.hasAnyAuthorityAndCheckAuth(Authority.USER);
  }
});

onMounted(() => {
  try {
    const raw = localStorage.getItem(widgetOrderKey.value);
    if (raw) {
      const persisted = JSON.parse(raw);
      if (Array.isArray(persisted) && persisted.length) {
        // Nur gültige Keys übernehmen
        const valid = persisted.filter(k => defaultWidgetOrder.includes(k));
        const missing = defaultWidgetOrder.filter(k => !valid.includes(k));
        widgetOrder.value = [...valid, ...missing];
      }
    }
  } catch {}
  try {
    const rawVis = localStorage.getItem(visibilityKey.value);
    if (rawVis) {
      const vis = JSON.parse(rawVis);
      visibleMap.value = { ...visibleMap.value, ...vis };
    }
  } catch {}
  try {
    const rawFav = localStorage.getItem(favoritesKey.value);
    if (rawFav) {
      const favs = JSON.parse(rawFav);
      if (Array.isArray(favs)) {
        favoriteItems.value = favs;
      }
    }
  } catch {}

  // Backend-Daten für Overview laden (nur wenn Feature aktiv)
  if (featureOverview.value) {
    OverviewService.getKpis()
      .then(data => {
        if (data && typeof data === 'object') {
          kpiItems.value = [
            { label: 'Verträge', value: data.contracts ?? 0 },
            { label: 'Dokumente', value: data.documents ?? 0 },
            { label: 'Fällige Termine', value: data.upcomingAppointments ?? 0 },
          ];
        }
      })
      .catch(() => {});
    OverviewService.getCashflowTrend()
      .then(data => {
        if (data && Array.isArray(data.values)) {
          cashflowValues.value = data.values;
        }
      })
      .catch(() => {});
  }
});

let dragSourceKey = null;
const onDragStart = (index) => {
  if (!editMode.value) return;
  const visible = widgetOrder.value.filter(k => visibleMap.value[k]);
  dragSourceKey = visible[index];
};
const onDrop = (targetIndex) => {
  if (!editMode.value) return;
  const visible = widgetOrder.value.filter(k => visibleMap.value[k]);
  if (!dragSourceKey) return;
  const sourceIndexInVisible = visible.indexOf(dragSourceKey);
  if (sourceIndexInVisible === -1 || sourceIndexInVisible === targetIndex) { dragSourceKey = null; return; }
  const targetKey = visible[targetIndex];
  const full = [...widgetOrder.value];
  const srcFull = full.indexOf(dragSourceKey);
  const tgtFull = full.indexOf(targetKey);
  if (srcFull === -1 || tgtFull === -1) { dragSourceKey = null; return; }
  full.splice(srcFull, 1);
  full.splice(tgtFull, 0, dragSourceKey);
  widgetOrder.value = full;
  dragSourceKey = null;
  try { localStorage.setItem(widgetOrderKey.value, JSON.stringify(widgetOrder.value)); } catch {}
};

const updateVisibleMap = (map) => {
  visibleMap.value = map;
  try { localStorage.setItem(visibilityKey.value, JSON.stringify(visibleMap.value)); } catch {}
};
const resetOrder = () => {
  widgetOrder.value = [...defaultWidgetOrder];
  try { localStorage.setItem(widgetOrderKey.value, JSON.stringify(widgetOrder.value)); } catch {}
};

</script>

