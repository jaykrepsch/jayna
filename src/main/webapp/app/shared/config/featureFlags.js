// Simple feature flag helper with localStorage override
// Usage:
// - Defaults live in DEFAULT_FLAGS
// - Override at runtime via localStorage.setItem('flags.FEATURE_X', 'true'|'false')

const DEFAULT_FLAGS = {
  FEATURE_OVERVIEW: true,
  FEATURE_TILE_CUSTOM_GROUPTYPES: false,
};

export function getFeatureFlag(key, defaultValue) {
  try {
    const stored = window.localStorage.getItem(`flags.${key}`);
    if (stored === 'true') return true;
    if (stored === 'false') return false;
  } catch {}
  if (key in DEFAULT_FLAGS) return DEFAULT_FLAGS[key];
  return defaultValue;
}

export function setFeatureFlag(key, value) {
  try {
    window.localStorage.setItem(`flags.${key}`, value ? 'true' : 'false');
  } catch {}
}

export function resetFeatureFlag(key) {
  try {
    window.localStorage.removeItem(`flags.${key}`);
  } catch {}
}

export function listFeatureFlags() {
  return { ...DEFAULT_FLAGS };
}


