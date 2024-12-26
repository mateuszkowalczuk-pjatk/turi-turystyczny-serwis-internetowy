import React from 'react'
import { useTranslation } from 'react-i18next'
import { TouristicPlaceType } from '../../../types/touristicPlace.ts'
import styles from './TourismTouristicPlaceTypeSelect.module.css'

interface Props {
    value: TouristicPlaceType
    onChange: (value: TouristicPlaceType) => void
    disabled: boolean
}

const TourismTouristicPlaceTypeSelect = ({ value, onChange, disabled }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.panel}>
            <select
                className={styles.select}
                value={value}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) =>
                    onChange(e.target.value as unknown as TouristicPlaceType)
                }
                disabled={disabled}
            >
                <option value={TouristicPlaceType.UNASSIGNED}>{t('tourism.touristic-place-type-title')}</option>
                <option value={TouristicPlaceType.GUESTHOUSE}>{t('tourism.touristic-place-type-guesthouse')}</option>
                <option value={TouristicPlaceType.APARTMENT}>{t('tourism.touristic-place-type-apartment')}</option>
                <option value={TouristicPlaceType.COTTAGES}>{t('tourism.touristic-place-type-cottages')}</option>
                <option value={TouristicPlaceType.HOTEL}>{t('tourism.touristic-place-type-hotel')}</option>
                <option value={TouristicPlaceType.BB}>{t('tourism.touristic-place-type-bb')}</option>
                <option value={TouristicPlaceType.HOSTEL}>{t('tourism.touristic-place-type-hostel')}</option>
            </select>
        </div>
    )
}

export default TourismTouristicPlaceTypeSelect
