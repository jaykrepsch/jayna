<template>
  <EditButton @click="clickEdit()" />
</template>

<script setup>
import EditButton from '@/components/buttons/EditButton.vue';
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

const clickEdit = () => { 
  console.log('OverviewTableEditButton clicked!', props);
  // Edit button clicked with params
  
  // Bestimme die korrekte Update-Route basierend auf der Entity
  let routeName;
  if (props.entityName === 'realestate') {
    routeName = 'RealEstateUpdate';
  } else if (props.entityName === 'financeaccount') {
    routeName = 'FinanceAccountUpdate';
  } else {
    routeName = props.entityName ? `${props.entityName.charAt(0).toUpperCase() + props.entityName.slice(1)}Update` : 'ContractUpdate';
  }
  
  
  
  // Verwende die neue einfache Route für die Update-Ansicht
  console.log('Attempting to navigate to:', routeName, 'with params:', { entityid: props.entityId });
  try {
    router.push({
      name: routeName,
      params: {
        entityid: props.entityId
      }
    });
    console.log('Router navigation successful');
  } catch (err) {
    console.error('Router navigation failed:', err);
    
    // Fallback: Verwende direkte URL-Navigation
    const path = `/${props.entityName}/update/${props.entityId}`;
    console.log('Trying fallback navigation to:', path);
    router.push(path);
  }
};

</script>