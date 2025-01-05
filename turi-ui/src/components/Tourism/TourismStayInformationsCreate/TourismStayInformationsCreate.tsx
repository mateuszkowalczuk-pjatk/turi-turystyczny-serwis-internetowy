import { useTranslation } from 'react-i18next'
import { useState } from 'react'
import Input from '../../Shared/Controls/Input'
import { StayInformation } from '../../../types/stay.ts'
import { stayService } from '../../../services/stayService.ts'
import styles from './TourismStayInformationsCreate.module.css'

interface Props {
    createStayInformation: (stayInformation: StayInformation) => void
    stayId?: number
    modify?: boolean
}

const TourismStayInformationsCreate = ({ createStayInformation, stayId, modify }: Props) => {
    const { t } = useTranslation()
    const [information, setInformation] = useState<string>('')

    const handleCreate = async (information: string) => {
        if (modify && stayId)
            await stayService.createStayInformation({
                stayId: stayId,
                information: information
            })
    }

    return (
        <div className={styles.create}>
            <Input
                type={'text'}
                name={'new'}
                placeholder={t('tourism.touristic-place-stay-offer-informations-placeholder')}
                value={information}
                onChange={(e) => setInformation(e.target.value)}
                required={false}
            />
            <button
                className={styles.button}
                type="button"
                onClick={() => {
                    handleCreate(information).catch((error) => error)
                    createStayInformation({ stayId: 0, information })
                    setInformation('')
                }}
            >
                {t('tourism.touristic-place-guaranteed-services-add')}
            </button>
        </div>
    )
}

export default TourismStayInformationsCreate
