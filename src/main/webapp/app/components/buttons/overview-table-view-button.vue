<template>
  <button
    @click="clickView()"
    class="inline-flex items-center px-2 py-1 text-xs font-medium rounded text-black hover:text-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
    title="Anzeigen"
  >
    <EyeIcon class="h-4 w-4" />
  </button>
</template>

<script setup>
import { EyeIcon } from '@heroicons/vue/24/outline';
import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  catId: Number,
  subCatId: Number,
  subCatGrpId: Number,
  grpTpId: Number,
  entityId: Number,
  entityName: String, // Neue Prop für den Entity-Namen
});

const clickView = () => { 
  // View button clicked with params
  
  // Bestimme die korrekte Route basierend auf der Entity
  let routeName;
  if (props.entityName === 'document') {
    routeName = 'DocumentView';
  } else if (props.entityName === 'realestate') {
    routeName = 'RealEstateView';
  } else if (props.entityName === 'financeaccount') {
    routeName = 'FinanceAccountView';
  } else if (props.entityName === 'contract') {
    routeName = 'ContractView';
  } else if (props.entityName === 'contact') {
    routeName = 'ContactView';
  } else {
    routeName = props.entityName ? `${props.entityName.charAt(0).toUpperCase() + props.entityName.slice(1)}View` : 'ContractView';
  }
  
  // Verwende die neue einfache Route für die Detailansicht
  try {
    router.push({
      name: routeName,
      params: {
        entityid: props.entityId
      }
    });
  } catch (_) {
    // Fallback auf Pfad, falls die benannte Route nicht korrekt aufgelöst wird
    router.push(`/document/view/${props.entityId}`);
  }
};
</script> 