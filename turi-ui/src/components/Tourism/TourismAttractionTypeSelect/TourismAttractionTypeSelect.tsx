import React from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { AttractionType } from '../../../types/attraction.ts'
import styles from './TourismAttractionTypeSelect.module.css'

interface Props {
    value: AttractionType
    onChange: (value: AttractionType) => void
    disabled: boolean
}

const TourismAttractionTypeSelect = ({ value, onChange, disabled }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.panel}>
            <select
                className={styles.select}
                value={value}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) =>
                    onChange(e.target.value as unknown as AttractionType)
                }
                disabled={disabled}
            >
                <option value={AttractionType.UNASSIGNED}>
                    {t('tourism.touristic-place-attraction-type-unassigned')}
                </option>
                <option value={AttractionType.RELAX}>{t('tourism.touristic-place-attraction-type-relax')}</option>
                <option value={AttractionType.SPORT}>{t('tourism.touristic-place-attraction-type-sport')}</option>
                <option value={AttractionType.RECREATION}>
                    {t('tourism.touristic-place-attraction-type-recreation')}
                </option>
                <option value={AttractionType.ENTERTAINMENT}>
                    {t('tourism.touristic-place-attraction-type-entertainment')}
                </option>
                <option value={AttractionType.FOOD}>{t('tourism.touristic-place-attraction-type-food')}</option>
                <option value={AttractionType.EVENT}>{t('tourism.touristic-place-attraction-type-event')}</option>
                <option value={AttractionType.CHILDREN}>{t('tourism.touristic-place-attraction-type-children')}</option>
                <option value={AttractionType.OTHER}>{t('tourism.touristic-place-attraction-type-other')}</option>
            </select>
        </div>
    )
}

export default TourismAttractionTypeSelect
