import { useTranslation } from 'react-i18next'
import Checkbox from '../../Controls/Checkbox'
import styles from './PersonalGender.module.css'
import { Gender } from '../../../types'

interface Props {
    gender: Gender | null
    handleGenderChange: (gender: Gender) => void
}

const PersonalGender = ({ gender, handleGenderChange }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.gender}>
            <Checkbox
                checked={gender === Gender.FEMALE}
                onChange={() => handleGenderChange(Gender.FEMALE)}
                text={t('signup-personal.woman')}
            />
            <Checkbox
                checked={gender === Gender.MALE}
                onChange={() => handleGenderChange(Gender.MALE)}
                text={t('signup-personal.man')}
            />
        </div>
    )
}

export default PersonalGender
